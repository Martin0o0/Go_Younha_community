package com.example.goyounhacom.Config;


import com.example.goyounhacom.Config.handler.LoginFailuereHandler;
import com.example.goyounhacom.Config.handler.LoginSuccessHandler;
import com.example.goyounhacom.Service.PrincipalDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration //하나 이상의 빈을 등록하기 위해 Configuration 선언 //환경설정파일을 뜻하는 어노테이션이기도 함.
@EnableWebSecurity //스프링 시큐리티 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;
    private final ObjectMapper objectMapper;

    @Bean // 필드단위니까 Bean
    public PasswordEncoder passwordEncoder() { //비밀번호 암호화
        return new BCryptPasswordEncoder(); //비밀번호 암호화를 해주어야 함.
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/h2/**", "/api/auth/**", "/api/hellopost/**", "/mainpost/**", "/auth/**").permitAll()
                .anyRequest().authenticated() //나머지의 요청에 대해서는 인증받은 사람만 접속 가능.
                .and()
                .csrf().ignoringAntMatchers("/h2/**") //h2에 대해서만 crsf 끄기.
                .disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'")) //header name 오류 방지.
                .frameOptions().disable()
                .and()
                .formLogin().disable() //폼 로그인 좆까라 선언.
                .httpBasic().disable() //특정리소스 접그할 때마다 쳐물어보는 아디비번 좆까라고 선언.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션 상태 유지 안함
        //.loginPage("/auth/user/log-in") //해당하는 로그인 페이지로 이동

        http.addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class);

    }

    @Bean
    public AuthenticationManager authenticationManager() {//- AuthenticationManager 등록
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder());//PasswordEncoder로는 bCryPasswordEncoder를 사용
        provider.setUserDetailsService(principalDetailService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginFailuereHandler loginFailuereHandler(){
        return new LoginFailuereHandler();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }


    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter(){
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler()); //성공핸들러 적용
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailuereHandler()); //실패해들러 적용
        return jsonUsernamePasswordLoginFilter;
    }


}
