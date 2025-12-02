package com.example.student.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.constant.Grade;

@SpringBootTest
@Disabled
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testInsert(){
        StudentDTO dto = StudentDTO.builder()
        .name("유하람")
        .addr("Seoul")
        .gender("F")
        .grade(Grade.JUNIOR)
        .build();

        System.out.println(studentService.insert(dto));
    }

    @Test
    public void testRead(){
        System.out.println(studentService.read(3L));
    }

    @Test
    public void testReadAll(){
        // List<StudentDTO> list = studentService.readAll();
        // for (StudentDTO studentDTO : list) {
        //     System.out.println(studentDTO);
        // }
        studentService.readAll().forEach(student -> System.out.println(student));
    }

    @Test
    public void testUpdate(){
        StudentDTO dto = StudentDTO.builder()
        .id(10L)
        .grade(Grade.SENIOR)
        .name("최지우")
        .build();
        System.out.println(studentService.update(dto));
    }

    @Test
    public void testDelete(){
        studentService.delete(8L);
    }
}
