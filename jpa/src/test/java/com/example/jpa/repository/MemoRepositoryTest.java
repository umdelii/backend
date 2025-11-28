package com.example.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

@SpringBootTest //test야 알려주기
public class MemoRepositoryTest {
    @Autowired
    private MemoRepository memoRepository;

    // crud 테스트

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Memo memo = Memo.builder()
            .menoText("memo text"+i)
            .build();

            memoRepository.save(memo);
        }
    }

    @Test
    public void updateTest(){
        // id = 3의 text 수정
        Memo memo = memoRepository.findById(3L).get();
        memo.changeMenoText("변경 text");

        memoRepository.save(memo);
    }

    @Test
    public void deleteTest(){
        memoRepository.deleteById(10L);
    }

    @Test
    public void readTest(){
        Memo memo = memoRepository.findById(4L).get();
        System.out.println(memo);
    }

    @Test
    public void readAllTest(){
        List<Memo> memos = memoRepository.findAll();
        memos.forEach((memo)->{
            System.out.println(memo);
        });
    }
}
