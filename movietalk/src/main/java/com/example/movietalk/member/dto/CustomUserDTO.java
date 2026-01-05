package com.example.movietalk.member.dto;

import com.example.movietalk.member.entity.constant.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Email(message = "メール形式は不正です")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 3, max = 8, message = "ニックネームは3~8字の全角ひらがな又はカタカナ、半角英数字でお願いします")
    private String nickname;

    private Role role;

}
