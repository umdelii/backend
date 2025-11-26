package com.example.web.common;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@Log4j2
public class HomeController {
    
    @GetMapping("/")
    public String getHome() {
        return "home";
    }
    
    @GetMapping("/separate")
    public void getSeparate() {
        log.info("separate 페이지 요청");
    }
    
}
