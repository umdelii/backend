package com.example.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void testInsert() {
        Team team = Team.builder().name("team1").build();
        teamRepository.save(team);

        TeamMember member = TeamMember.builder().name("정이안").team(team).build();
        teamMemberRepository.save(member);
    }

    @Test
    public void testInsert2() {
        Team team = teamRepository.findById(3L).get();

        TeamMember member = TeamMember.builder().team(team).name("유하람").build();
        teamMemberRepository.save(member);
    }

    @Test
    public void testInsert3() {
        Team team = Team.builder().name("team3").build();
        teamRepository.save(team);
    }

    @Test
    public void testRead() {
        Team team = teamRepository.findById(1L).orElseThrow();
        System.out.println(team);

        // 외래키가 적용된 테이블이기 때문에 join을 바로 해서 코드실행
        TeamMember member = teamMemberRepository.findById(1L).orElseThrow();
        System.out.println(member);

        // 팀원 -> 팀 조회
        // System.out.println("팀 명 " + member.getTeam().getName());
    }

    @Test
    public void testUpdate() {
        // 팀 이름 변경
        Team team = teamRepository.findById(1L).orElseThrow();
        team.setName("Karasu");
        teamRepository.save(team);
        System.out.println(team);

        // 소속 팀을 변경하기
        TeamMember member = teamMemberRepository.findById(2L).orElseThrow();
        member.setTeam(Team.builder().id(2L).build());
        System.out.println(teamMemberRepository.save(member));
    }

    @Test
    public void testDelete() {
        // 팀 삭제
        // DataIntegrityViolationException: could not execute statement [Cannot delete
        // or update a parent row: a foreign key constraint fails => parent row 있어서
        // delete나 update 못한댄다
        // teamRepository.deleteById(2L);
        // => 1. 팀원(자식)을 먼저 삭제하거나, 2. 삭제하려는 팀의 팀원을 다른 팀으로 이동시켜서 걸리는 자식을 없앤다

        // 2. 팀 이동 시키기 후 삭제
        List<TeamMember> result = teamMemberRepository.findByTeam(teamRepository.findById(1L).get());
        result.forEach(member -> {
            member.setTeam(teamRepository.findById(2L).get());
            teamMemberRepository.save(member);
        });
        teamRepository.deleteById(1L);
    }

    @Test
    public void testDelete2() {
        // 팀 삭제
        // DataIntegrityViolationException: could not execute statement [Cannot delete
        // or update a parent row: a foreign key constraint fails => parent row 있어서
        // delete나 update 못한댄다
        // teamRepository.deleteById(2L);
        // => 1. 팀원(자식)을 먼저 삭제하거나, 2. 삭제하려는 팀의 팀원을 다른 팀으로 이동시켜서 걸리는 자식을 없앤다

        // 1. 팀원 삭제 후 팀 삭제
        List<TeamMember> result = teamMemberRepository.findByTeam(teamRepository.findById(2L).get());
        result.forEach(m -> {
            teamMemberRepository.delete(m);
        });
        teamRepository.deleteById(2L);
    }

    // @OnetoMany를 알기 위한 test
    @Test
    @Transactional
    public void testRead2() {
        // 팀 --> 멤버조회
        Team team = teamRepository.findById(3L).get();
        // System.out.println(team);
        System.out.println(team.getTeamMembers());
        // @Transactional 달고 @OneToMany(mappedBy = "team")하니
        // 잘나오기는 하나 select구문이 두번실행됨
    }

    @Test
    public void testRead3() {
        // 팀 --> 멤버조회( join 구문 걸어서 처리시키고 싶음)
        Team team = teamRepository.findById(3L).get();
        System.out.println(team);
        // System.out.println(team.getTeamMembers());
    }

    @Test
    @Transactional
    public void testRead4() {
        TeamMember teamMember = teamMemberRepository.findById(3L).get();
        // System.out.println(teamMember);
        System.out.println(teamMember.getTeam());
    }

    // cascade 개념 적용 후
    @Test
    public void testCascadeInsert() {
        Team team = Team.builder().name("team4_cascade").build();
        team.getTeamMembers().add(TeamMember.builder().name("김주은").team(team).build());
        teamRepository.save(team);
    }

    @Test
    public void testCascadeTest() {
        teamRepository.deleteById(4L);
    }

    // orphanRemoval = true 적용 후
    @Test
    @org.springframework.transaction.annotation.Transactional
    @Commit
    public void testOrphanTest() {
        Team team = teamRepository.findById(3L).get();
        team.getTeamMembers().remove(0);
        teamRepository.save(team);
    }

    @Test
    @org.springframework.transaction.annotation.Transactional
    @Commit
    public void testCascadeUpdate() {
        // dirty checking
        Team team = teamRepository.findById(5L).get();
        team.setName("tws");

        TeamMember teamMember = team.getTeamMembers().get(0);
        teamMember.setName("김도훈");
    }
}
