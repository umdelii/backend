package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoRestController {
    private final MemoService memoService;

    @GetMapping("/{mno}")
    public MemoDTO getSample1(@PathVariable("mno") Long id) {

        MemoDTO dto = memoService.read(id);

        return dto;
    }

    @GetMapping("")
    public List<MemoDTO> getList() {
        log.info("list 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

    // @RequestBody : json => 자바 객체로 매핑
    // http://localhost:8080/memo + POST
    @PostMapping("")
    public ResponseEntity<Long> postCreate(@RequestBody MemoDTO dto) {
        log.info("삽입 {}", dto);

        Long id = memoService.insert(dto);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    // put, delete는 rest에서만 가능
    @PutMapping("")
    public ResponseEntity<Long> put(@RequestBody MemoDTO dto) {
        log.info("put {}", dto);
        Long id = memoService.modify(dto);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<String> delete(@PathVariable("mno") Long id) {
        log.info("삭제 {}", id);

        memoService.remove(id);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}