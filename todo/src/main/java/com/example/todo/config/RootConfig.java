package com.example.todo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // spring 설정 파일
public class RootConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true) // 필드명 같으면 매핑해줘라 true
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // getter setter 없이도
                                                                                               // private필드 접근가능하게 해줘
                .setMatchingStrategy(MatchingStrategies.LOOSE); // 매칭를 좀 루즈하게(느슨하게)해줘라 ex. userName == user_name
        return modelMapper;
    }
}
