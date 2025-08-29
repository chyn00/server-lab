package com.server.lab.simulation.application;

import org.springframework.stereotype.Service;

@Service
public class BugService {
  static String URL_NAME;

  //OOM 재연 메서드
  public void repeatedExternalApiCall(String s){
    URL_NAME+=s;

    //todo: external Api Call
  }
}
