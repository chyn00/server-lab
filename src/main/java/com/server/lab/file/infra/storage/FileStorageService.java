package com.server.lab.file.infra.storage;

import com.server.lab.config.S3Utils;
import com.server.lab.file.infra.storage.model.UploadResult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// 먼저 서버 사이드 업로드 방식 사용(presigned url, 스트림처리 등 고려)
@Service
public class FileStorageService {

  private final S3Utils s3Utils;
  private final Executor executor;

  public FileStorageService(@Qualifier("s3FileUploadExecutor") Executor executor, S3Utils s3Utils) {
    this.executor = executor;
    this.s3Utils = s3Utils;
  }

  /**
   * 파일을 전체 업로드하고
   * 1. 업로드 실패파일이 있다면 실패파일 목록을 return(내부는 성공파일도 롤백)
   * 2. 업로드 실패파일이 없다면 성공한 전체 목록을 return
   **/
  public List<String> s3uploadAndMetaDataSave(List<MultipartFile> files) {
    List<CompletableFuture<UploadResult>> futures = new ArrayList<>();

    // 1차적으로 전체 업로드
    for (MultipartFile file : files) {
      CompletableFuture<UploadResult> future = CompletableFuture.supplyAsync(() -> {
        String fileKey = null;
        try {
          fileKey = s3Utils.upload(file);

          // TODO: 메타데이터 저장
          return UploadResult
              .builder()
              .success(true)
              .fileName( file.getOriginalFilename())
              .fileKey(fileKey)
              .errorMessage(null)
              .build();
        } catch (Exception e) {
          return UploadResult
              .builder()
              .success(false)
              .fileName( file.getOriginalFilename())
              .fileKey(fileKey)
              .errorMessage(e.getMessage())
              .build();
        }
      }, executor);

      futures.add(future);
    }

    // 비동기 작업 완료 대기
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    // 비동기 결과를 List로 반환
    List<UploadResult> results = futures.stream()
        .map(CompletableFuture::join)
        .toList();

    // 실패한 파일 필터링
    List<String> failedFiles = results.stream()
        .filter(r -> !r.isSuccess())
        .map(UploadResult::getFileName)
        .toList();

    // 성공한 파일 롤백 (삭제)
    if (!failedFiles.isEmpty()) {
      results.stream()
          .filter(r -> r.isSuccess() && r.getFileName() != null)
          .forEach(r -> {
            try {
              s3Utils.delete(r.getFileKey());
            } catch (Exception e) {
              // TODO: 보상 트랜잭션(아웃박스 패턴 등으로 삭제 실패 기록)
            }
          });

      //실패한 경우 실패한 key 리턴
      return failedFiles;
    }

    // 모두 성공한 경우 성공한 key 리턴
    return results.stream()
        .map(UploadResult::getFileKey)
        .toList();
  }

}
