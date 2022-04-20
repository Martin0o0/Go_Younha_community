package com.example.goyounhacom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //jpa auditing 설정
@SpringBootApplication
public class GoyounhacomApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoyounhacomApplication.class, args);
    }

}
