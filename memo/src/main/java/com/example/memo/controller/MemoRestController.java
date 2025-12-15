package com.example.memo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoRestController {
    private final MemoService memoService;

    @GetMapping("/{id}")
    public MemoDTO getSample1(@PathVariable Long id) {

        MemoDTO dto = memoService.read(id);

        return dto;
    }

    @GetMapping("/list2")
    public List<MemoDTO> getList() {
        log.info("list 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

}
