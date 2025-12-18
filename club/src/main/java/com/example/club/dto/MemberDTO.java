package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MemberDTO extends User {

    // member entity 情報 + 認証情報(security)
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    // 認証情報
    // extends User
    public MemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.email = username; // 名前が異なるから教えてあげる
    }
}
