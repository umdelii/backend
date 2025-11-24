package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;




@Log4j2
@Controller 
public class HomeController {
    @GetMapping("/home")
    public void getHome() {
       log.info(("home 요청"));  // System.out.pirntIn()
    }

    @GetMapping("/add")
    public String getAdd() {
        return "result";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("getCalc");
    }
    
    @PostMapping("/calc")
    public void postCalc(int num1) {
        log.info("postCalc {}",num1);
    }
    
    
}
