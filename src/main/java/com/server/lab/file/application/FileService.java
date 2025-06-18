package com.server.lab.file.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  public List<String> saveFiles(List<MultipartFile> files) {
    List<String> messages = new ArrayList<>();

    if (files == null || files.isEmpty()) {
      messages.add("업로드할 파일을 선택해주세요.");
    } else {
      for (MultipartFile file : files) {
        if (!file.isEmpty()) {
          messages.add("업로드 성공: " + file.getOriginalFilename());
        } else {
          messages.add("빈 파일입니다.");
        }
      }
    }

    return messages;
  }
}
