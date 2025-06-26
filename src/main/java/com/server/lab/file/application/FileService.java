package com.server.lab.file.application;

import com.server.lab.file.infra.storage.FileStorageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

  private final FileStorageService fileStorageService;

  public List<String> saveFiles(List<MultipartFile> files) {
    List<String> messages = new ArrayList<>();

    if (this.hasInvalidUploadFiles(files)) {
      messages.add("업로드할 파일을 선택해주세요.");
    } else {
      return fileStorageService.s3uploadAndMetaDataSave(files);
    }

    return messages;
  }

  public boolean hasInvalidUploadFiles(List<MultipartFile> files) {
    if (files == null || files.isEmpty()) {
      return true;
    }

    //file 내부 이름도 비어있는 파일이 하나라도 있으면 false
    return files.stream().anyMatch(file ->
        file == null ||
            file.isEmpty() ||
            !StringUtils.hasText(file.getOriginalFilename())
    );
  }
}
