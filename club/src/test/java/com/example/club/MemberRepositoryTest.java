package com.example.club;

import static org.mockito.ArgumentMatchers.matches;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.club.entity.Member;
import com.example.club.entity.constant.ClubMemberRole;
import com.example.club.repository.MemberRepository;

@SpringBootTest
// @Disabled
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .name("username" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            member.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                member.addMemberRole(ClubMemberRole.MANAGER);
            }
            if (i > 9) {
                member.addMemberRole(ClubMemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }

    @Test
    // @Transactional(readOnly = true)
    public void loginTest() {
        Member member = memberRepository.findByEmailAndFromSocial("user10@gmail.com", false).get();
        System.out.println(member);
    }
}
