package com.example.movietalk.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    private final ReviewService reviewService;

    // 指定映画の口コミ登録
    @PostMapping("/{mno}")
    public Long postReview(@PathVariable Long mno, @RequestBody ReviewDTO dto) {

        log.info("dto {}", dto);

        Long rno = reviewService.insertRow(dto);

        return rno;
    }

    // 指定映画の口コミ全部取得
    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getAllReviews(@PathVariable Long mno) {

        log.info("mno {}", mno);

        List<ReviewDTO> list = reviewService.getList(mno);

        return list;
    }

    // 指定映画の口コミ修正
    @GetMapping("/{mno}/{rno}")
    public ReviewDTO getReview(@PathVariable Long rno) {

        log.info("修正 rno {}", rno);

        return reviewService.getRow(rno);
    }

    @PutMapping("/{mno}/{rno}")
    public ResponseEntity<Long> putReview(@PathVariable Long rno, @RequestBody ReviewDTO dto) {

        log.info("修正 dto {}", dto);

        rno = reviewService.updateRow(dto);

        // 상태코드 전송
        // return new ResponseEntity<Long>(rno, HttpStatus.OK);
        return new ResponseEntity<Long>(rno, HttpStatusCode.valueOf(200));
    }

    // 指定映画の口コミ削除
    @DeleteMapping("/{mno}/{rno}")
    public ResponseEntity<String> deleteReview(@PathVariable Long rno) {

        log.info("削除 rno {}", rno);

        reviewService.deleteRow(rno);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
