package com.example.web.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "아이디는 4~8자리 사이로 작성해주세요")
    @Length(min = 4,max = 8,message = "아이디는 4~8자리 사이로 작성해주세요")
    private String id;

    @NotEmpty(message = "특수문자 포함 8~16자리로 작성해주세요")
    @Length(min = 8,max = 16,message = "특수문자 포함 8~16자리로 작성해주세요")
    private String password;

    // @NotNull(message = "이메일 형식에 맞춰 작성해주세요") -> 공백도 유효함
    @NotBlank(message = "이메일 형식에 맞춰 작성해주세요") 
    private String email;
}
