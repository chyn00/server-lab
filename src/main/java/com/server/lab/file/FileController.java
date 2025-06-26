package com.server.lab.file;

import com.server.lab.file.application.FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @GetMapping("/")
  public String test(){
    return "server-lab";
  }

  //prg 패턴을 활용(post만 사용하면 재요청이 보내질 수 있다. 리다이렉트를 통해, 중복 업로드 방지)
  @GetMapping("/upload")
  public String showUploadForm() {
    return "upload-form"; // 타임리프 리졸버 통해서 html실행(위치 : templates/upload-form.html)
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("files") List<MultipartFile> files, RedirectAttributes redirectAttributes) {
    List<String> resultMessages = fileService.saveFiles(files);
    redirectAttributes.addFlashAttribute("messages", resultMessages); // Flash: 리다이렉트 후 1회만 유지됨
    return "redirect:/upload";
  }
}

