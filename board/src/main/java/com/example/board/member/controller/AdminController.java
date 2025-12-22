package com.example.board.member.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class AdminController {
    @GetMapping("/admin/manage")
    public void getAdmin() {
        log.info("admin form 呼び出し");
    }

}
