package com.example.memo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoServiceTest {
    @Autowired
    private MemoService memoService;

    @Test
    public void readAllTest(){
        memoService.readAll().forEach(memo->System.out.println(memo));
    }
}
