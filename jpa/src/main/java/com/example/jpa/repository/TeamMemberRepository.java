package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    // JpaRepository 여기 기본제공 메소드말고 내가 따로 원하는 메소드를 만든다
    // 팀 정보를 이용해 해당 팀 소속 전체 팀원 찾기
    List<TeamMember> findByTeam(Team team);
}
