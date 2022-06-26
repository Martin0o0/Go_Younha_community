package com.example.goyounhacom.Config;



import com.example.goyounhacom.Service.PrincipalDetailService;
import com.example.goyounhacom.Service.customOAuth2UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //하나 이상의 빈을 등록하기 위해 Configuration 선언 //환경설정파일을 뜻하는 어노테이션이기도 함.
@EnableWebSecurity //스프링 시큐리티 설정 활성화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)//prePostEnabled = true 설정은 로그인 여부를 판별하기 위해 사용했던 @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요하다.
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Spring Security Filter Chain을 사용한다는 것을 명시해줘야 한다.
    // WebSecurityConfigurerAdapter 상속받은 Configuration 객체를 일단 만들어주고 거기에 @EnableWebSecurity 어노테이션을 달아주면 된다.


    private final PrincipalDetailService principalDetailService;
    private final ObjectMapper objectMapper;
    private final customOAuth2UserService customOAuth2UserService;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/image/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin().and() //stomp를 사용하기 위해서
                .authorizeRequests()
                // URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                // 나머지의 요청에 대해서는 인증받은 사람만 접속 가능.
                .and()
                .csrf().ignoringAntMatchers("/api/**") //api로 시작하는 엔드포인트는 csrf 인증을 무시한다.
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .and()//해당하는 로그인 페이지로 이동//폼 로그인 좆까라 선언.
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()//Oauth2방식으로 로그인
                .userInfoEndpoint() //성공했다면 그 정보를 가져올떄의 방식을 정함
                .userService(customOAuth2UserService); //가공할 방식을 지정 -> 아래에서 지정한 authenticationmananger와 비슷한 원리. -> userService방시을 지정한다.

//                //세션 상태 유지 안함


        http.rememberMe().tokenValiditySeconds(60 * 60 * 7).userDetailsService(principalDetailService); //유저 쿠키 유지.

    }

    @Bean
    public AuthenticationManager authenticationManager() {//- AuthenticationManager 등록 ->인증을 만들고 처리하는 인터페이스.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder);//PasswordEncoder로는 bCryPasswordEncoder를 사용 암호화는 이친구로 설정.
        provider.setUserDetailsService(principalDetailService); //유저 인증절차는 이친구에게 넘김.
        return new ProviderManager(provider); //인증은 provider에게 넘김.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 스프링 시큐리티의 인증절차를 수행함.
        auth.userDetailsService(principalDetailService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception { //세션 유지 => 회원 정보 수정 후에도 계속 유지됨.
        return super.authenticationManagerBean();
    }
}
