package com.example.board.reply.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.reply.dto.ReplyDTO;
import com.example.board.reply.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@Log4j2
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    // bno를 이용해 전체 댓글 가져오기
    @GetMapping("/board/{bno}")
    public List<ReplyDTO> getList(@PathVariable Long bno) {
        log.info("{}번 글 댓글 요청", bno);

        return replyService.getList(bno);
    }

    // rnoを使って特定のリプ持ってくる
    @GetMapping("/{rno}")
    public ReplyDTO getReply(@PathVariable Long rno) {
        log.info("{}番目のリプ要請", rno);

        return replyService.getRow(rno);
    }

    @PutMapping("/{rno}")
    public Long putReply(@RequestBody ReplyDTO dto) {
        log.info("수정 요청 {}", dto);

        Long rno = replyService.update(dto);

        return rno;
    }

    @PostMapping("/new")
    public Long postMethodName(@RequestBody ReplyDTO dto) {
        log.info("추가 요청 {}", dto);
        Long rno = replyService.create(dto);

        return rno;
    }

    @DeleteMapping("/{rno}")
    public String deleteReply(@PathVariable Long rno) {
        log.info("{}番目 削除", rno);
        replyService.delete(rno);
        return "success";
    }
}
