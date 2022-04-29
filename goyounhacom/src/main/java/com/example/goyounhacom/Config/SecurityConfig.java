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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //하나 이상의 빈을 등록하기 위해 Configuration 선언 //환경설정파일을 뜻하는 어노테이션이기도 함.
@EnableWebSecurity //스프링 시큐리티 설정 활성화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //prePostEnabled = true 설정은 로그인 여부를 판별하기 위해 사용했던 @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요하다.
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
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated() //나머지의 요청에 대해서는 인증받은 사람만 접속 가능.
                .and()
                .csrf().ignoringAntMatchers("/h2/**") //h2에 대해서만 crsf 끄기.
                .disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'")) //header name 오류 방지.
                .frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .and()//해당하는 로그인 페이지로 이동//폼 로그인 좆까라 선언.
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
                //세션 상태 유지 안함


//        http.addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class);

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 스프링 시큐리티의 인증절차를 수행함.
        auth.userDetailsService(principalDetailService).passwordEncoder(passwordEncoder());
    }
}
