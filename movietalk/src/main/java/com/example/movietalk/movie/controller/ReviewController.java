package com.example.movietalk.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    // 指定映画の口コミ登録
    @PostMapping("/{mno}")
    public String postReview(@PathVariable Long mno) {

        return null;
    }

    // 指定映画の口コミ全部取得
    @GetMapping("/{mno}/all")
    public String getAllReview(@PathVariable Long mno) {
        return new String();
    }

    // 指定映画の口コミ修正
    @GetMapping("/{mno}/{rno}")
    public String getReview(@PathVariable Long rno) {
        return new String();
    }

    @PutMapping("/{mno}/{rno}")
    public String putReview(@PathVariable Long rno, @RequestBody String entity) {

        return entity;
    }

    // 指定映画の口コミ削除
    @DeleteMapping("/{mno}/{rno}")
    public String deleteReview(@PathVariable Long rno) {
        return null;
    }

}
