package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void insertTest(){
        Student student = Student.builder()
        .name("정지우")
        .addr("seoul")
        .gender("F")
        .build();

        // save() : insert, update 작업 시 호출
        studentRepository.save(student);

        // delete from ~ 호출
        // studentRepository.deleteById(2L);

        // select 호출 (행1개)
        // studentRepository.findById(1L);
        // select 호출 (행 전체)
        // studentRepository.findAll();
    }
}
