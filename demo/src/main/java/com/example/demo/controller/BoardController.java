package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@Controller
// 공통된 경로 따로 빼기
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/add")
    public void getAdd() {
        log.info("/board/add 요청");
    }
    
    // 동일한 경로로는 지정 불가능 
    // @GetMapping("/board/add")
    // public void getAdd2() {
    //     log.info("/board/add 요청");
    // }

    @GetMapping("/modify")
    public void getModify() {
        log.info("/board/modify 요청");
    }

    @GetMapping("/read") // == http://localhost:8080/board/read
    public void getRead(@ModelAttribute("no") int no) {
        log.info("/board/read 요청 {}",no);
        // model.addAttribute("no", no);

    }
}
