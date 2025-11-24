package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class BoardController {
    @GetMapping("/board/add")
    public void getAdd() {
        log.info("/board/add 요청");
    }
    
    // 동일한 경로로는 지정 불가능 
    // @GetMapping("/board/add")
    // public void getAdd2() {
    //     log.info("/board/add 요청");
    // }
}
