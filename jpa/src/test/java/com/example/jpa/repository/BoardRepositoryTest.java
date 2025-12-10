package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        for (int i = 1; i < 11; i++) {
            Board board = Board.builder()
                    .title("title " + i)
                    .content("abcdefg")
                    .writer("writer" + i)
                    .build();

            boardRepository.save(board);
        }
    }

    @Test
    public void updateTest() {
        // title, content 수정
        Board board = boardRepository.findById(8L).get();
        board.setTitle("update title");
        boardRepository.save(board);

        board = boardRepository.findById(5L).get();
        board.setContent("가나다라마바사");
        boardRepository.save(board);
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(4L);
    }

    @Test
    public void readTest() {
        System.out.println(boardRepository.findById(7L));
    }

    @Test
    public void readAllTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void testQueryMethod() {
        System.out.println("findByTitle" + boardRepository.findByTitle("update title"));

        System.out.println("findByContent" + boardRepository.findByContent("가나다라마바사"));

        System.out.println("findByTitleEndingWith" + boardRepository.findByTitleEndingWith("3"));

        System.out.println("findByTitleContainingAndIdGreaterThanOrderByIdDesc"
                + boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc("5", 0L));

        System.out.println("findByWriterContaining" + boardRepository.findByWriterContaining("writer"));

        System.out.println("findByTitleContainingOrContentContaining"
                + boardRepository.findByTitleContainingOrContentContaining("7", "abcd"));
    }

    @Test
    public void testQueryMethod2() {
        System.out.println("findByTitle2" + boardRepository.findByTitle2("update title"));

        System.out.println("findByContent2" + boardRepository.findByContent2("가나다라마바사"));

        System.out.println("findByTitleEndingWith2" + boardRepository.findByTitleEndingWith2("3"));

        System.out.println("findByTitleContainingAndIdGreaterThanOrderByIdDesc2"
                + boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc2("5", 0L));

        System.out.println("findByWriterContaining2" + boardRepository.findByWriterContaining2("writer"));

        System.out.println("findByTitleContainingOrContentContaining2"
                + boardRepository.findByTitleContainingOrContentContaining2("7", "abcd"));
    }

    @Test
    public void testQueryMethod25() {
        System.out.println(boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc25("title", 7L));
    }

    @Test
    public void testqueryMethod251() {
        // List<Object[]> result = boardRepository.findByTitle3("title");
        // for (Object[] objects : result) {
        // // System.out.println(Arrays.toString(objects));
        // String title = (String) objects[0];
        // String writer = (String) objects[1];
        // System.out.println(title + " " + writer);
        // }

        boardRepository.findByTitle3("title").forEach(array -> {
            // System.out.println(Arrays.toString(array));
            String title = (String) array[0];
            String writer = (String) array[1];
            System.out.println(title + " : " + writer);
        });
    }
}
