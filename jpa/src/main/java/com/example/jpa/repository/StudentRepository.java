package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Student;

// DAO 역할 
// 기본적인 crud메소드는 이미 정의가 되어있음
public interface StudentRepository extends JpaRepository<Student,Long>{
    
}
