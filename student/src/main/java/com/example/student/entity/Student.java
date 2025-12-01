package com.example.student.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.student.entity.constant.Grade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class) // LocalDateTime
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Table(name="stutbl")
@Entity // == 이 클래스는 테이블과 연동되어있음을 알려주는 어노테이션
public class Student {
    // @GeneratedValue(strategy = GenerationType.AUTO) => GenerationType.AUTO가 default값 (Hibernate가 자동으로 생성)

    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL,Oracle의 기본 자동 증가처럼 해줌

    // 밑 두 줄은 내가 임의로 시퀀스를 생성시키고 증가값까지 설정해서, 그 시퀀스를 불러다 쓰는 방식
    // @SequenceGenerator(name = "stu_seq_gen",sequenceName = "stu_seq",allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stu_seq_gen")
    @Id
    private long id;

    @Column(length = 50,nullable = false)
    // @Column(columnDefinition = "varchar(50) not null unique") -> sql 쿼리문처럼 직접 입력도 가능
    private String name;

    @Column
    private String addr;

    // db check 조건 걸기
    @Column(columnDefinition = "varchar(1) constraint chk_gender check (gender in ('M','F'))")
    private String gender;

    // grade column 추가하기 => FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    // enum으로 그냥 하면 index값(정확하게는 ordinal)(숫자)이 들어가서 값이 뭔지 몰라!
    @Enumerated(EnumType.STRING) // 기본값은 숫자인 0 부터 시작
    @Column
    private Grade grade;

    @CreatedDate // spring boot 설정 후 삽입
    private LocalDateTime createDateTime; //datetime(6)

    @LastModifiedDate // 마지막 수정일시
    private LocalDateTime upDateTime; 


    public void changeName(String name) {
        this.name = name;
    }

    public void changeGrade(Grade grade) {
        this.grade = grade;
    }
}
