package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.constant.RoleType;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            
            Member member = Member.builder()
            .userId("P"+i)
            .name("guest")
            .role(RoleType.MEMBER)
            .build();

            memberRepository.save(member);
        }
    }

    @Test
    public void updateTest(){
        // id = 9의 role을 admin으로 수정
        Optional<Member> result = memberRepository.findById(9L);
        // result.get();
        result.ifPresent(member -> {
            member.updateRole(RoleType.ADMIN);
            memberRepository.save(member);
        });
    }

    // 수업x 개인용(name guest뒤에 숫자 안붙임)
    @Test
    public void updateTest2(){
        for (Long i = 1L; i < 11L; i++) {
            Member member = memberRepository.findById(i).get();
            member.updateUserId("P"+i);
            memberRepository.save(member);
        }
    }
    // 컬럼 잘못넣었으니 update 화살표 함수로 다시 수정해보자(얘도 수업아님 내가 만들어본거..)
    // 화살표 함수 실패! (아직 안배운거때문에 컴파일 오류뜸)
    @Test
    public void updateTest3(){
        for (Long i = 1L; i < 11L; i++) {
            Member member = memberRepository.findById(i).get();                
            member.updateName("guest"+i);
            memberRepository.save(member);            
        }
    }
    
    @Test
    public void deleteTest(){
        memberRepository.deleteById(10L);
    }

    @Test
    public void readTest(){
        Optional<Member> result = memberRepository.findById(5L);
        result.ifPresent(member -> System.out.println(member));
    }

    @Test
    public void readAllTest(){
        List<Member> members = memberRepository.findAll();
        members.forEach(member -> System.out.println(member));
    }
}
