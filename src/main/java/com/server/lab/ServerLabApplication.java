package com.server.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.server.lab")
public class ServerLabApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerLabApplication.class, args);
  }

}
