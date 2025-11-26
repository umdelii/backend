package com.example.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.member.dto.loginDTO;

import jakarta.servlet.http.HttpSession;


@Controller
@Log4j2
public class MemberController {

        @GetMapping("/member/login")
    public void getLogin() {
        log.info("login 요청");
    }

    // http 특징 : 요청들어오면 응답하고 연결 끊어
    // HttpSession : http 프로토콜 단점 해결 
    // ex.로그인, 장바구니
    // 서버 쪽에 정보 저장

    // 브라우저 정보 저장 => cookie

    @PostMapping("/member/login")
    public String postLogin(loginDTO dto, HttpSession session) {
        //id,password 가져오기
        log.info("login post {}",dto);
        // 내 정보를 세션에 담기
        session.setAttribute("loginDto", dto);
        //로그인 버튼 누르면 첫페이지로 이동
        return "redirect:/";
    }
    

}
