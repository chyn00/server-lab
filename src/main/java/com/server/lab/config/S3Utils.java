package com.server.lab.config;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3Utils {

  private final S3Client s3Client;//aws config에 정의

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  /**
   * 파일 업로드
   */
  public String upload(MultipartFile file) throws IOException {
    String originalFilename = file.getOriginalFilename();//파일 이름
    String key = UUID.randomUUID() + "_" + originalFilename;//파일 키

    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .contentType(file.getContentType())
        .build();

    s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    return key;
  }

  /**
   * 파일 삭제
   */
  public void delete(String key) {
    DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    s3Client.deleteObject(deleteRequest);
  }
}
