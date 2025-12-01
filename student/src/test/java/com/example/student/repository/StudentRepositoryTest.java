package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.entity.Student;
import com.example.student.entity.constant.Grade;

@SpringBootTest
@Disabled
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
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            System.out.println(student);
        }
    }


    @Test
    public void updateTest(){
        Optional<Student> result = studentRepository.findById(2L);
        Student student = result.get();
        student.changeGrade(Grade.FRESHMAN);

        studentRepository.save(student);
    }

    @Test
    public void insertTest(){

        for (int i = 1; i < 11; i++) {
            
            Student student = Student.builder()
            .name("정이안"+i)
            .addr("Seoul")
            .gender("F")
            .grade(Grade.FRESHMAN)
            .build();
    
            studentRepository.save(student);
        }
    }

    @Test
    public void deleteTest(){
        studentRepository.deleteById(2L);
    }
}
