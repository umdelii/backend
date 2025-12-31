package com.example.movietalk.member.dto;

import com.example.movietalk.member.entity.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDTO {
    private Long mid;

    private String email;

    private String password;

    private String nickname;

    private Role role;

}
