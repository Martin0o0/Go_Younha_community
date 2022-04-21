package com.example.goyounhacom.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //하나 이상의 빈을 등록하기 위해 Configuration 선언
@EnableWebSecurity //스프링 시큐리티 설정 활성화
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean // 필드단위니까 Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호 암호화를 해주어야 함.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/h2","/api/auth/**", "/api/hellopost/**")
                .permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .formLogin()
//                //.loginPage("/auth/user/log-in") //해당하는 로그인 페이지로 이동
//                //.loginProcessingUrl("/") //해당주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
//                .defaultSuccessUrl("/"); //성공하면 해당 url로 이동 //
    }
}
