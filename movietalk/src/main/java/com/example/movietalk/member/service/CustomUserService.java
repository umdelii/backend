package com.example.movietalk.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.member.dto.AuthUserDTO;
import com.example.movietalk.member.dto.CustomUserDTO;
import com.example.movietalk.member.dto.PasswordDTO;
import com.example.movietalk.member.entity.Member;
import com.example.movietalk.member.repository.MemberRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CustomUserService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    // login 作業
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("login 要請 {}", username);

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("会員情報が存在しません。"));

        CustomUserDTO customUserDTO = CustomUserDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();

        AuthUserDTO authUserDTO = new AuthUserDTO(customUserDTO);

        // return User.builder()
        // .username(member.getEmail())
        // .password(member.getPassword())
        // .build();

        return authUserDTO;
    }

    // 会員登録
    public Long join(CustomUserDTO dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(dto.getRole())
                .build();

        return memberRepository.save(member).getMid();
    }

    // nickname 変更
    public void changeNickname(CustomUserDTO dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow();

        member.setNickname(dto.getNickname());
    }

    // password 変更
    public void changePassword(PasswordDTO dto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow();

        // currentPasswordが正しいか確認
        if (passwordEncoder.matches(dto.getCurrentPassword(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        } else {
            throw new IllegalStateException("パスワードが正しくありません");
        }
    }

    public void leave(CustomUserDTO dto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow();

        // passwordが正しいか確認
        if (passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            reviewRepository.deleteByMember(member);
            memberRepository.delete(member);
        } else {
            throw new IllegalStateException("パスワードが正しくありません");
        }

    }
}
