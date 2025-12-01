package com.example.memo.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled // build 시점에는 실행하지 말기
public class MemoServiceTest {
    @Autowired
    private MemoService memoService;

    @Test
    public void readAllTest(){
        memoService.readAll().forEach(memo->System.out.println(memo));
    }
}
