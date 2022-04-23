package com.example.goyounhacom.Config;


import com.example.goyounhacom.Config.auth.PrincipalDetail;
import com.example.goyounhacom.Service.PrincipalDetailService;
import com.example.goyounhacom.domain.Users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration //하나 이상의 빈을 등록하기 위해 Configuration 선언
@EnableWebSecurity //스프링 시큐리티 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

    @Bean // 필드단위니까 Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); //비밀번호 암호화를 해주어야 함.
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(bCryptPasswordEncoder()); //
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/h2/**", "/api/auth/**", "/api/hellopost/**").permitAll()
                .anyRequest().authenticated() //나머지의 요청에 대해서는 인증받은 사람만 접속 가능.
                .and()
                .csrf().ignoringAntMatchers("/h2/**") //h2에 대해서만 crsf 끄기.
                .disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'")) //header name 오류 방지.
                .frameOptions().disable()
                .and()
                .formLogin() //일단 무시.
                .disable();
                //.loginPage("/auth/user/log-in") //해당하는 로그인 페이지로 이동

    }



}
