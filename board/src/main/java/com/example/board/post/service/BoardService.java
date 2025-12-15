package com.example.board.post.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    // crud
    @Transactional(readOnly = true)
    public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        // @Query 사용
        // Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        // @querydsl 사용(검색기능)
        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageable);

        // 번호 , 제목(댓글개수), 작성자, 작성일
        // 위 Page<Object[]> result 얘 받아서 dto로 바꿔줘
        Function<Object[], BoardDTO> f = en -> entityToDto((Board) en[0], (Member) en[1], (Long) en[2]);
        List<BoardDTO> dtoList = result.stream().map(f).collect(Collectors.toList());
        long totalcount = result.getTotalElements();

        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalcount)
                .build();

        return pageResultDTO;
    }

    @Transactional(readOnly = true)
    public BoardDTO getRow(Long bno) {
        // content , reply
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        BoardDTO dto = entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);

        return dto;
    }

    public Long insert(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return boardRepository.save(board).getBno();
    }

    public BoardDTO update(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getBno()).orElseThrow();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        return dto;
    }

    public void delete(BoardDTO dto) {
        // 게시글 삭제
        // 자식으로 댓글이 존재
        replyRepository.deleteByBno(dto.getBno());
        boardRepository.deleteById(dto.getBno());
    }

    // entity -> dto 바꿀때 필요한 정보(컬럼)만 꺼내쓰는 메소드
    private BoardDTO entityToDto(Board board, Member member, Long replyCnt) {
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .createDateTime(board.getCreateDateTime())
                .updateDateTime(board.getUpdateDateTime())
                .replyCnt(replyCnt != null ? replyCnt.intValue() : 0)
                .build();

        return dto;
    }
}
