package com.server.lab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

@Configuration
public class AwsConfig {

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Bean
  AwsBasicCredentials awsBasicCredentials(){
    return AwsBasicCredentials
        .builder()
        .accessKeyId(this.accessKey)
        .secretAccessKey(this.secretKey)
        .build();
  }
}
