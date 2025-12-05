package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "teamMembers")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    // mappdeBy를 안넣으면 각 클래스끼리 join을 두번해버림, 따라서 mappedBy = "주인 클래스의 내 필드 변수명"으로
    // 무조건!
    // 넣어서 알려줘야함
    @Builder.Default
    private List<TeamMember> teamMembers = new ArrayList<>();
    // 객체를 리스트도 만들어 초기화하는거랑 그냥 필드선언하는거랑 뭐가 다를까?
    // private TeamMember teamMember;
}
