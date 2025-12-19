package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.club.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

// security 設定クラス
@Configuration // スプリングの設定クラスだよ
@Log4j2
@EnableWebSecurity // すべてのウェブ要請に security filter chain 適用
public class SecurityConfig {

    @Bean // == インスタンス生成
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // http.authorizeHttpRequests(authorize
        // ->authorize.anyRequest().authenticated());
        // どんな(誰の)リクエスト(anyRequest)でも認証が必要(authenticated)
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/img/**", "/member/auth").permitAll()
                .requestMatchers("/member/**").hasRole("USER")
                .requestMatchers("/manager/**").hasAnyRole("MANAGER")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN"))
                // .httpBasic(Customizer.withDefaults()); // login画面の基本形態
                // .formLogin(Customizer.withDefaults());
                .formLogin(login -> login
                        .loginPage("/member/login").permitAll()
                        // .defaultSuccessUrl("/", true)
                        .successHandler(loginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(loginSuccessHandler())) // ソーシャルログインができるようになる
                .logout(logout -> logout
                        // logout postに処理
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/")
                        // セッション消して
                        .invalidateHttpSession(true)
                        // クッキ消して
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 運営、実務に使用
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 練習用、単一アルゴリズム使用
        // return new BCryptPasswordEncoder();
    }
}
