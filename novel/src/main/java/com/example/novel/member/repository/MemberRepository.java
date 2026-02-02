package com.example.novel.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novel.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
