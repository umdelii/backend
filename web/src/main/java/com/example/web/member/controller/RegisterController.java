package com.example.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.member.dto.RegisterDTO;

import jakarta.validation.Valid;



@Controller
@Log4j2
public class RegisterController {
    @GetMapping("/member/register")
    public void getMethodName(RegisterDTO dto) {
        log.info("register 페이지 호출");
    }

    // @Valid 붙여서 이거 검증해야하는 값이야 알려주기
    // BindingResult : 유효성에 걸릴때 돌아가게하는 객체
    @PostMapping("/member/register")
    public String postMethodName(@Valid RegisterDTO dto, BindingResult result) {
        log.info("회원가입 요청 {}",dto);
        
        if (result.hasErrors()) {
            return "/member/register";
        }

        return "redirect:/member/login";
    }
    
    
}
