package com.server.lab.file;

import com.server.lab.file.application.FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @GetMapping("/")
  public String test(){
    return "server-lab";
  }

  @GetMapping("/upload")
  public String showUploadForm() {
    return "upload-form"; // 타임리프 리졸버 통해서 html실행(위치 : templates/upload-form.html)
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("files") List<MultipartFile> files, Model model) {
    List<String> resultMessages = fileService.saveFiles(files);
    model.addAttribute("messages", resultMessages);
    return "upload-form";
  }
}

