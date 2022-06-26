package com.example.goyounhacom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing //jpa auditing 설정
@SpringBootApplication //마법의 자동 의존성 설정이 된다 => 반드시 필요한 어노테이션

public class GoyounhacomApplication {

    @Bean // 필드단위니까 Bean
    public PasswordEncoder passwordEncoder() { //비밀번호 암호화
        return new BCryptPasswordEncoder(); //비밀번호 암호화를 해주어야 함.
    }

    public static void main(String[] args) {
        SpringApplication.run(GoyounhacomApplication.class, args);
    }

}
