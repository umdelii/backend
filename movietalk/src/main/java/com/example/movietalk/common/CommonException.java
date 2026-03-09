package com.example.movietalk.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.log4j.Log4j2;

// controller 모두에게 ADVICE
// @ControllerAdvice
@Log4j2
public class CommonException {

    // 404 error 対応ページ
    @ExceptionHandler(NoResourceFoundException.class)
    public String notFound() {
        log.info("404 error");
        return "except/url404";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception e, Model model) {
        log.info("500 error");
        model.addAttribute("e", e);
        return "except/500";
    }

}
