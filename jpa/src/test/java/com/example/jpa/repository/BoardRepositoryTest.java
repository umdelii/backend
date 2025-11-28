package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Board board = Board.builder()
            .title("title "+i)
            .content("abcdefg")
            .writer("writer"+i)
            .build();
            
            boardRepository.save(board);
        }
    }

    @Test
    public void updateTest(){
        // title, content 수정
        Board board = boardRepository.findById(8L).get();
        board.setTitle("update title");
        boardRepository.save(board);

        board = boardRepository.findById(5L).get();
        board.setContent("가나다라마바사");
        boardRepository.save(board);
    }

    @Test
    public void deleteTest(){
        boardRepository.deleteById(4L);
    }

    @Test
    public void readTest(){
        System.out.println(boardRepository.findById(7L));
    }

    @Test
    public void readAllTest(){
        boardRepository.findAll().forEach(board->System.out.println(board));
    }

}
