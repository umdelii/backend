package com.example.web.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    // @NotBlank(message = "아이디는 4~8자리 사이로 작성해주세요")
    // @Length(min = 4,max = 8,message = "아이디는 4~8자리 사이로 작성해주세요")
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+[\\d])[A-za-z\\d]{4,8}", message = "아이디는 영문, 숫자 포함 4~8자리 사이로 작성해주세요")
    private String id;
    
    // @NotEmpty(message = "특수문자 포함    8~16자리로 작성해주세요")
    // @Length(min = 8,max = 16,message = "특수문자 포함 8~16자리로 작성해주세요")
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)(?=.+[#$%@!^])[A-za-z\\d#$%]{8,16}",message = "특수문자(#,$,%,@,!,^ 사용가능) 포함 8~16자리로 작성해주세요")
    private String password;


    // @NotNull(message = "이메일 형식에 맞춰 작성해주세요") -> 공백도 유효함
    // @NotBlank(message = "이메일 형식에 맞춰 작성해주세요") 
    @Email(message = "이메일 형식에 맞춰 작성해주세요(공란불가)")
    private String email;

    @Pattern(regexp = "^[가-힣]{2,8}$",message = "2~8자의 한글만 가능")
    private String name;

    @Min(value = 14, message = "만 14세 이상 회원가입 가능")
    @Max(value = 120, message = "만 14세 이상 회원가입 가능")
    @NotNull(message = "공란불가")
    private Integer age;
}
