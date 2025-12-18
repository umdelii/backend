package com.example.security.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    // http://localhost:8080 : みんなに開放
    // http://localhost:8080/sample/guest : みんなに開放
    // http://localhost:8080/sample/member : メンバーだけに開放
    // http://localhost:8080/sample/admin : アドミンだけに開放
    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest 要請");
    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member 要請");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 要請");
    }

    @GetMapping("/info")
    public void getInfo() {
        log.info("info 要請");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("login form 要請");
    }

}
