package com.example.movietalk.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movietalk.member.dto.AuthUserDTO;
import com.example.movietalk.member.dto.CustomUserDTO;
import com.example.movietalk.member.dto.PasswordDTO;
import com.example.movietalk.member.entity.constant.Role;
import com.example.movietalk.member.service.CustomUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final CustomUserService customUserService;

    // login + GET
    @GetMapping("/login")
    public void getLogin() {

        log.info("login form");
    }

    @GetMapping("/register")
    public void getRegister(CustomUserDTO dto) {
        log.info("会員登録フォーム要請");
    }

    @PostMapping("/register")
    public String postRegister(@Valid CustomUserDTO dto, BindingResult result) {
        log.info("会員登録申し込み {}", dto);

        if (result.hasErrors()) {
            return "/member/register";
        }

        // service作業
        try {
            dto.setRole(Role.MEMBER);
            Long mid = customUserService.join(dto);
            return "redirect:/member/login";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "/member/register";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public void getProfile() {
        log.info("profile 呼び出し");
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public void getEdit() {
        log.info("edit 呼び出し");
    }

    // nickname 変更
    @PostMapping("/edit/nickname")
    @PreAuthorize("isAuthenticated()")
    public String postNickname(CustomUserDTO dto) {
        customUserService.changeNickname(dto);

        // securityContext情報アップデート
        Authentication authentication = getAuthentication();
        AuthUserDTO auth = (AuthUserDTO) authentication.getPrincipal();
        auth.getCustomUserDTO().setNickname(dto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    // pwd 変更
    @PostMapping("/edit/password")
    @PreAuthorize("isAuthenticated()")
    public String postPwd(PasswordDTO dto, HttpSession session, RedirectAttributes rttr) {
        log.info("パスワード変更フォーム");

        try {
            customUserService.changePassword(dto);
            session.invalidate(); // 現在ログイン情報削除
        } catch (Exception e) {
            log.info(e.getMessage());
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }

        return "redirect:/member/login";
    }

    @GetMapping("/leave")
    @PreAuthorize("isAuthenticated()")
    public void getLeave() {
        log.info("脱退フォーム");
    }

    @PostMapping("/leave")
    @PreAuthorize("isAuthenticated()")
    public String postLeave(CustomUserDTO dto, HttpSession session, RedirectAttributes rttr) {
        log.info("脱退");

        try {
            customUserService.leave(dto);
            session.invalidate();
        } catch (Exception e) {
            log.info(e.getMessage());
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/leave";
        }

        return "redirect:/";
    }

    // ログインの後SecurityContextに保存されてる情報確認(開発用)
    @GetMapping("/auth")
    @ResponseBody
    public Authentication getAuthenticationInfo() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

    private Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }
}
