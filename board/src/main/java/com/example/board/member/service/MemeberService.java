package com.example.board.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.member.dto.MemberDTO;
import com.example.board.member.dto.RegisterDTO;
import com.example.board.member.entity.Member;
import com.example.board.member.entity.constant.MemberRole;
import com.example.board.member.repository.MemberRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Data
public class MemeberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("clubservice username : {}", username);

        // Optional<Member> result = memberRepository.findByEmailAndFromSocial(username,
        // false);
        // result.get(); // Optionalを取り除く方法1

        Member member = memberRepository.findByEmailAndFromSocial(username, false)
                .orElseThrow(() -> new UsernameNotFoundException("メール確認"));

        MemberDTO dto = new MemberDTO(member.getEmail(), member.getPassword(), member.isFromSocial(),
                member.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()));

        dto.setName(member.getName());

        return dto;
    }

    public void register(RegisterDTO dto) throws IllegalStateException {
        // email 重複検査
        Optional<Member> result = memberRepository.findById(dto.getEmail());
        if (result.isPresent()) {
            throw new IllegalStateException("登録済みのemailです。");
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .fromSocial(false)
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);

    }

}
