package com.example.board.reply.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.post.entity.Board;
import com.example.board.reply.dto.ReplyDTO;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public Long create(ReplyDTO dto) {
        Reply reply = dtoToEntity(dto);
        return replyRepository.save(reply).getRno();
    }

    @Transactional(readOnly = true)
    public List<ReplyDTO> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> result = replyRepository.findByBoardOrderByRno(board);

        // reply => replyDTO 변경 후 리턴
        // 1) ModelMapper : 구조(컬럼명과 개수등)가 완전히 동일할 때 편하다
        // 2) 직접 변환하는 메소드를 만들어라

        // return
        // result.stream().map(ReplyService::entityToDTO).collect(Collectors.toList());
        // 밑에 있는 this는 ReplyServie(클래스,인스턴스)를 선언!했기에 동시 map()과 동시 처리 가능
        // this = private ReplySerive replyService; 같은 의미, 나(클래스)를 선언
        return result.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReplyDTO getRow(Long rno) {
        Reply reply = replyRepository.findById(rno).orElseThrow();
        return entityToDTO(reply);
    }

    public Long update(ReplyDTO dto) {

        Reply reply = replyRepository.findById(dto.getRno()).get();
        reply.setText(dto.getText());

        return reply.getRno();
    }

    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }

    // dto <=> entity メソッド
    private ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .bno(reply.getBoard().getBno())
                // .replyer(reply.getReplyer())
                .replyerEmail(reply.getReplyer().getEmail())
                .replyerName(reply.getReplyer().getName())
                .text(reply.getText())
                .createDateTime(reply.getCreateDateTime())
                .updateDateTime(reply.getUpdateDateTime())
                .build();

        return dto;
    }

    private Reply dtoToEntity(ReplyDTO dto) {
        Member member = Member.builder()
                .email(dto.getReplyerEmail())
                .build();

        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                // .replyer(dto.getReplyer())
                .replyer(member)
                .board(Board.builder().bno(dto.getBno()).build())
                .build();

        return reply;
    }
}
