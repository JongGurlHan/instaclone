package com.jghan.instaclone.web.dto.auth;

import com.jghan.instaclone.domain.user.User;
import lombok.Data;

@Data
public class SignupDto {
    private  String username;
    private  String password;
    private  String email;
    private  String name;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();

    }
}
