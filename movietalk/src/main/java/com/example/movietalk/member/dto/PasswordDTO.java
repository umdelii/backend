package com.example.movietalk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordDTO {
    private String email;
    private String currentPassword;
    private String newPassword;
}
