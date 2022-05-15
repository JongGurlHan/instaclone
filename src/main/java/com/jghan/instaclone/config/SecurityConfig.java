package com.jghan.instaclone.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 비활성화
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated() //인증이 필요한 url
                .anyRequest().permitAll() //어떤 요청이라도 허용.
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .defaultSuccessUrl("/"); //로그인 정상됐을때 이동하는 url
    }

}