package com.jghan.instaclone.web.dto.user;

import com.jghan.instaclone.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    //username, email은 안받는다.
    @NotBlank
    private String name; //필수
    @NotBlank
    private String password; //필수
    private String website;
    private String bio;
    private String phone;
    private String gender;

    //코드 수정이 필요할 예정
    public User toEntity(){
        return User.builder()
                .name(name) //이름을 기재 안하면 문제 -> validation체크
                .password(password) //비번을 기재 안하면 문제 -> validation체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
