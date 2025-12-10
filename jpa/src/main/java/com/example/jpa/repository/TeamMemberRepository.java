package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    // JpaRepository 여기 기본제공 메소드말고 내가 따로 원하는 메소드를 만든다
    // 팀 정보를 이용해 해당 팀 소속 전체 팀원 찾기
    List<TeamMember> findByTeam(Team team);

    // 외래키로 연관된 테이블 @Query 날리기
    @Query("select m,t from TeamMember m join m.team t where t = ?1")
    List<Object[]> findByMemberAndTeam(Team team);

    @Query("select m,t from TeamMember m join m.team t where t.id = ?1")
    List<Object[]> findByMemberAndTeam2(Long id);

    @Query("select m,t from TeamMember m left join m.team t")
    List<Object[]> findByMemberAndTeam3();
}
