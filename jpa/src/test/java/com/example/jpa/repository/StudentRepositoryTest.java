package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.constant.Grade;

@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    
    @Test
    public void readTest(){
        Student student = studentRepository.findById(2L).get();
        System.out.println(student); // com.example.jpa.entity.Student@35e0d91e -> ToString 안해서 이상한거뜬다
    }

    @Test
    public void readAllTest(){
        // 전체 행(학생) 조회
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            System.out.println(student);
        }
    }


    @Test
    public void updateTest(){
        // Entity
        // update stutbl set 수정할컬럼=값 where id=1;
        Optional<Student> result = studentRepository.findById(2L);
        Student student = result.get();
        student.changeGrade(Grade.FRESHMAN);

        studentRepository.save(student);
    }

    @Test
    public void insertTest(){
        Student student = Student.builder()
        .name("정이안")
        .addr("seoul")
        .gender("F")
        .grade(Grade.SOPHOMORE)
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

    @Test
    public void deleteTest(){
        // 방법1
        // Student student = studentRepository.findById(2L).get();
        // studentRepository.delete(student);
        // 방법2
        studentRepository.deleteById(2L);
    }
}
