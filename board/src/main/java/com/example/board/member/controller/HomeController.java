package com.example.board.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "/home";
    }

    @GetMapping("/manager/info")
    public void getManager() {
    }
}
