package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoder() {
        String password = "1111";

        // 入力するパスワード => 暗号化
        String encodePass = passwordEncoder.encode(password);
        // {bcrypt}$2a$10$mkaHg./LroZKM4kNF9jLteRXemWI5zvz/o80RmLmNH.ddV1atuvk. : 片方向暗号化
        // $2a$10$NjCDirujdnR05fdMu.4H5OuGc9RY2amSsKfVs5atH2IOWWob78gqK
        System.out.println("raw password " + password + " / encodePass " + encodePass);

        System.out.println(passwordEncoder.matches(password, encodePass));
        System.out.println(passwordEncoder.matches("2222", encodePass));
    }
}
