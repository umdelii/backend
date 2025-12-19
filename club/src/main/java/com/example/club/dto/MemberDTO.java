package com.example.club.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MemberDTO extends User implements OAuth2User {

    // member entity 情報 + 認証情報(security)
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    // Oauth2Userが渡してくれるarrtのため
    private Map<String, Object> attr;

    // 認証情報
    // extends User
    public MemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.password = password;
        this.fromSocial = fromSocial;
        this.email = username; // 名前が異なるから教えてあげる
    }

    // Oauth2User
    public MemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    // social login (Oauth2User)
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
