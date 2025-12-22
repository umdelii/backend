package com.example.club.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {

    // 会員登録用DTO

    // member entity 情報 + 認証情報(security)
    @NotBlank(message = "emailを入力してください")
    // @Pattern(regexp =
    // "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    @Email(message = "email 確認")
    private String email;

    @NotBlank(message = "passwordを入力してください(8~16字の半客英語、数字含め)")
    private String password;

    @NotBlank(message = "名前を入力してください")
    private String name;

    private boolean fromSocial;

}
