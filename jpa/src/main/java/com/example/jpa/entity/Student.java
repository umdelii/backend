package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@EntityListeners(value = AuditingEntityListener.class) // LocalDateTime
@Builder
@Table(name="stutbl")
@Entity
public class Student {
    // @GeneratedValue(strategy = GenerationType.AUTO) => GenerationType.AUTO가 default값 (Hibernate가 자동으로 생성)

    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL,Oracle의 기본 자동 증가처럼 해줌

    // 밑 두 줄은 내가 임의로 시퀀스를 생성시키고 증가값까지 설정해서, 그 시퀀스를 불러다 쓰는 방식
    // @SequenceGenerator(name = "stu_seq_gen",sequenceName = "stu_seq",allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stu_seq_gen")
    @Id
    private long id;

    @Column(name = "sname",length = 50,nullable = false,unique = true)
    // @Column(columnDefinition = "varchar(50) not null unique") -> sql 쿼리문처럼 직접 입력도 가능
    private String name;

    @Column
    private String addr;

    // db check 조건 걸기
    @Column(columnDefinition = "varchar(1) constraint chk_gender check (gender in ('M','F'))")
    private String gender;

    @CreationTimestamp // Hibernate꺼 insert시 자동으로 일자 삽입하고 싶을때 사용
    private LocalDateTime createDateTime1; //datetime(6)

    @CreatedDate // spring boot 설정 후 삽입
    private LocalDateTime createDateTime2; //datetime(6)
}
