package com.example.board.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.board.member.dto.MemberDTO;
import com.example.board.member.entity.Member;
import com.example.board.member.entity.constant.MemberRole;
import com.example.board.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberOauth2Service extends DefaultOAuth2UserService {

    // ソーシャルログイン情報を会員登録で処理
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest {}", userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        // log.info("clientName : {}", clientName);
        // log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // log.info("=======================");
        // oAuth2User.getAttributes().forEach((k, v) -> {
        // log.info(k + " : " + v);
        // });

        // 1. googleからの情報からメール抽出
        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");

        }
        // 2. テーブルに同じメールがあるか確認(なかったら自動的に登録)
        Member member = saveSocialMember(email);

        // 3. entity -> dto
        MemberDTO dto = new MemberDTO(member.getEmail(), member.getPassword(), member.isFromSocial(),
                member.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()),
                oAuth2User.getAttributes());

        dto.setName(member.getName());

        return dto;
    }

    private Member saveSocialMember(String email) {
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;

    }
}
