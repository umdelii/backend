package com.example.movietalk.member.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthUserDTO extends User {

    private CustomUserDTO customUserDTO;

    public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // User 클래스에는 기본 정보밖에 담겨있지않기때문에 직접 커스텀해서 인증받을수있다
    public AuthUserDTO(CustomUserDTO customUserDTO) {
        super(customUserDTO.getEmail(), customUserDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + customUserDTO.getRole())));
        this.customUserDTO = customUserDTO;
    }

}
