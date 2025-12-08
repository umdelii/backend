package com.example.jpa.entity;

// import java.time.LocalDateTime;

// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.EntityListeners;
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

@Entity
@Table(name = "membertbl")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BaseEntity {
    // column
    // id, name(필수), age(필수), 역할(member,admin), 가입일자, 수정일자, 자기소개

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    // @CreatedDate
    // private LocalDateTime createDate;

    // @LastModifiedDate
    // private LocalDateTime updateDate;

    @Column(length = 2000)
    // clob 지정
    // @Lob
    private String description;

    public void updateRole(RoleType role) {
        this.role = role;
    }

    // 수업x 개인용(name guest뒤에 숫자 안붙임) 메소드
    public void updateUserId(String userId) {
        this.userId = userId;
    }

    // 컬럼 잘못넣음 ㅎㅎ;
    public void updateName(String name) {
        this.name = name;
    }

}
