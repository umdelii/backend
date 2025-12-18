package com.example.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    // fromSocial은 구글로그인으로 접근했는지 안했는지 여부 판단
    // @Query("select m from Member m where m.email = :email and m.fromSocial =
    // :fromSocial")
    @EntityGraph(attributePaths = { "roles" }, type = EntityGraphType.LOAD)
    Optional<Member> findByEmailAndFromSocial(String email, boolean fromSocial);

}
