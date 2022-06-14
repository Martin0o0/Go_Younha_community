package com.example.goyounhacom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //jpa auditing 설정
@SpringBootApplication //마법의 자동 의존성 설정이 된다 => 반드시 필요한 어노테이션

public class GoyounhacomApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoyounhacomApplication.class, args);
    }

}
