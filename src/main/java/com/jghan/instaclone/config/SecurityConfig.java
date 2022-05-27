package com.jghan.instaclone.config;


import com.jghan.instaclone.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration //IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 비활성화
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/follow/**", "/comment/**", "/api/**").authenticated() //인증이 필요한 url
                .anyRequest().permitAll() //어떤 요청이라도 허용.
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .defaultSuccessUrl("/") //로그인 정상됐을때 이동하는 url
                .and()
                .oauth2Login()// form 로그인도 하는데, oauth2로그인도 할꺼야
                .userInfoEndpoint() // oauth2로그인을 한다면 최종응답으로 회원정보를 바로 받을 수 있다. / code받고 access토큰 받는 과정을 신경안써도 된다.
                .userService(oAuth2DetailsService);
    }
}