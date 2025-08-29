package com.server.lab.simulation;

import com.server.lab.simulation.application.BugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BugController {
  private final BugService bugService;

  @GetMapping("/bug")
  public void externalApiCall(String s){
    bugService.repeatedExternalApiCall(s);
    log.info(s);
  }
}
