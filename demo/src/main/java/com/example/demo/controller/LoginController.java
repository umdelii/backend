package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginDTO;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@Log4j2
@RequestMapping("/member")
public class LoginController {
    @GetMapping("/login")
    public void getLogin() {
        log.info("login 호출");
    }
    
    // @PostMapping("/login")
    // public void postLogin(LoginDTO login) {
    //     // log.info("id : {}, password : {}",loginDto.getId(),loginDto.getPassword());
    //     log.info("{}", login);
    // }

    @PostMapping("/login")
    public void postLogin(@ModelAttribute("login") LoginDTO login) {
        log.info("{}", login);
    }
    

    // ?no=1&name=홍길동
    // @RequestParam = http 요청의 파라미터를 컨트롤러 메소드의 매개변수로 바인딩(연결)
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    //RequestMapping 이건 예전 방식
    // @RequestMapping(path="/test", method=RequestMethod.GET)
    // public String requestMethodName(@RequestParam String param) {
    //     return new String();
    // }
    
}
