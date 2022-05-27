package com.jghan.instaclone.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jghan.instaclone.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor  //Bean생성자
@Data
@Entity //DB에 테이블 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략이 db를 따라감
    private int id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio; //자기소개

    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    //1. 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마
    //2. User를 select할때 해당 유저 Id로 등록된 image들을 다 가져와
    //3. OneToMany에서는 FetchType.LAZY이 기본 옵션
    //Lazy = User를 SELECT할때 해당 유저 ID로 등록된 image들을 가져오지마 - 대신 getImages()함수의 image들이호출될때 가져와!
    //Eager = User를 SELECT할때 해당 유저 ID로 등록된 image들을 전부 Join해서 가져와!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) //Image내부에 있는 user를 무시하고 파싱한다. (User의 getter가 호출안되도록 막는다.)
    private List<Image> images;


    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
