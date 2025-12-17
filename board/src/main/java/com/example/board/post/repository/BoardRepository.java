package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.board.post.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    // "on" 구문 생략 기준 : 칼럼이 일치해야함
    @Query("select b,m from Board b join b.writer m")
    List<Object[]> getBoardWithWriter();

    // bno를 통해 댓글 가져오기
    @Query("select b,r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReplyWhereBno(Long bno);

    // 하나 조회 (querydsl 방식으로 바꿔써서 이제 안씀)
    // @Query("select b,m,count(r) from Board b left join b.writer m left join Reply
    // r on r.board = b where b.bno = :bno")
    // Object getBoardByBno(Long bno);

    // 목록화면 => 페이지 나누기 필요
    @Query(value = "select b,m,count(r) from Board b left join b.writer m left join Reply r on r.board = b group by b.bno", countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

}
