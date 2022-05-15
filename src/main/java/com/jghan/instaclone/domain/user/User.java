package com.jghan.instaclone.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor  //Bean생성자
@Data
@Entity //DB에 테이블 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략이 db를 따라감
    private int id;
    private String username;
    private String password;

    private String name;
    private String website;
    private String bio; //자기소개
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;


    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

}
