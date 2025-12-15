package com.example.memo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController // 데이터(json)만 주고 받기
@Log4j2
@RequiredArgsConstructor
// @RequestMapping("/memo2")
public class BasicController {
    private final MemoService memoService;
    // 자바 객체 <===> json

    @GetMapping("/hello")
    public String getHello() {
        return "hello world";
    }

    // 주소에 직접 넣는다 ex.http://localhost:8080/sample1/2
    @GetMapping("/sample1/{id}")
    public MemoDTO getSample1(@PathVariable Long id) {

        MemoDTO dto = memoService.read(id);

        return dto;
    }

    @GetMapping("/list")
    public List<MemoDTO> getList() {
        log.info("list 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

}
