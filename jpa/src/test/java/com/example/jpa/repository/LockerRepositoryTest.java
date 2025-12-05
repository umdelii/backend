package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {
    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Locker locker = Locker.builder().name("locker " + i).build();
            SportsMember sportsMember = SportsMember.builder().locker(locker).name("member " + i).build();

            lockerRepository.save(locker);
            sportsMemberRepository.save(sportsMember);
        });
    }

    @Test
    @Transactional
    public void readTest() {
        // 회원정보 조회
        // id = 1 회원만(locker x)
        SportsMember member = sportsMemberRepository.findById(2L).orElseThrow();
        // System.out.println(member);
        // locker 이름 조회
        System.out.println(member.getLocker().getName());
    }

    @Test
    public void readTest2() {
        // 전체 회원정보 조회
        sportsMemberRepository.findAll().forEach(m -> {
            // 회원정보만
            System.out.println(m);
            // locker 정보
            System.out.println(m.getLocker());
        });
    }

    @Test
    public void readTest3() {
        // locker => 회원 조회
        Locker locker = lockerRepository.findById(2L).get();
        // System.out.println(locker);
        // 회원 조회
        System.out.println(locker.getSportsMember().getName());
    }

    @Test
    public void readTest4() {
        // 전체 locker 조회
        lockerRepository.findAll().forEach(locker -> {
            // locker 정보
            System.out.println(locker);
            // member 정보
            // System.out.println(locker.getSportsMember());
        });
    }

    @Test
    public void deleteTest() {
        sportsMemberRepository.deleteById(1L);
        lockerRepository.deleteById(1L);
    }
}
