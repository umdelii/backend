package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // title = ?
    List<Board> findByTitle(String title);

    // content = ?
    List<Board> findByContent(String content);

    // title like %?
    List<Board> findByTitleEndingWith(String title);

    // title like %?% and id > 0 order by id desc
    List<Board> findByTitleContainingAndIdGreaterThanOrderByIdDesc(String title, Long id);

    // writer like %?%
    List<Board> findByWriterContaining(String writer);

    // title like %?% or content like %?%
    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // 쿼리생성 2. @Query : entity 기준이므로 대소문자 구문함(entity 클래스 이름 대문자 시작이니 제대로 )
    // @Query("select b from Board b where b.title = ?1")
    @Query("select b from Board b where b.title = :title")
    List<Board> findByTitle2(String title);

    @Query("select b.title, b.writer from Board b where b.title like %:title%")
    List<Object[]> findByTitle3(String title);

    @Query("select b from Board b where b.content = ?1")
    List<Board> findByContent2(String content);

    @Query("select b from Board b where b.title like %?1")
    List<Board> findByTitleEndingWith2(String title);

    // @Query("select b from Board b where b.title like %?1% and b.id > ?2 order by
    // id desc")
    @Query("select b from Board b where b.title like %:title% and b.id > :id order by id desc")
    List<Board> findByTitleContainingAndIdGreaterThanOrderByIdDesc2(String title, Long id);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining2(String writer);

    @Query("select b from Board b where b.title like %?1% or b.content like %?2%")
    List<Board> findByTitleContainingOrContentContaining2(String title, String content);

    // 쿼리생성 2.5 : @Query + nativeQuery
    @Query(value = "select b.* from boardtbl b where b.title like concat('%',:title,'%') and b.id > :id order by id desc", nativeQuery = true)
    // @NativeQuery(value = "select b.* from boardtbl b where b.title like
    // concat('%',:title,'%') and b.id > :id order by id desc")
    List<Board> findByTitleContainingAndIdGreaterThanOrderByIdDesc25(String title, @Param("id") Long id);
    // @Param은 -param머시기 오류뜨면 붙여라 컨트롤러의 @RequestParam과 동일역할

}
