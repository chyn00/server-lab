package com.server.lab.file.infra.storage.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadResult {

  private final boolean success;
  private final String fileName;
  private final String fileKey;
  private final String errorMessage;

}
