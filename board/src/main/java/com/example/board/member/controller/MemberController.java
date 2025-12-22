package com.example.board.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.member.dto.RegisterDTO;
import com.example.board.member.service.MemeberService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemeberService clubService;

    @GetMapping("/login")
    public void getLogin() {
        log.info("login form 呼び出し");
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    @GetMapping("/profile")
    public void getMember() {
        log.info("profile form 呼び出し");
    }

    @GetMapping("/register")
    public void getRegister(RegisterDTO dto) {
        log.info("register form 呼び出し");
    }

    @PostMapping("/register")
    public String postRegister(@Valid RegisterDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("register 呼び出し {}", dto);

        if (result.hasErrors()) {
            return "/member/register";
        }

        try {
            clubService.register(dto);
        } catch (Exception e) {
            rttr.addFlashAttribute("dupEmail", e.getMessage());
            return "redirect:/member/register";
        }
        return "redirect:/member/login";
    }

}
