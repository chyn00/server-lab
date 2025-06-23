package com.server.lab.file.infra.storage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

// 먼저 서버 사이드 업로드 방식 사용(presigned url, 스트림처리 등 고려)
@Service
public class FileStorageService {

  private final Executor executor;
  private final AwsBasicCredentials awsBasicCredentials;

  public FileStorageService(@Qualifier("s3FileUploadExecutor") Executor executor, AwsBasicCredentials awsBasicCredentials) {
    this.executor = executor;
    this.awsBasicCredentials = awsBasicCredentials;
  }

  public CompletableFuture<Void> s3uploadAndMetaDataSave(MultipartFile file) {
    return CompletableFuture.runAsync(() -> {
      try {
        // 1. 업로드
        // 2. 메타데이터 저장
      } catch (Exception e) {
        // 3. 실패 시 보상 트랜잭션 (S3 삭제), 아웃박스 패턴도 고려
        throw new RuntimeException("Upload failed", e);
      }
    }, executor);
  }

}
