package com.jghan.instaclone.domain.image;

import com.jghan.instaclone.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;
    private String postImageUrl; //사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 -Db에 그 저장된 경로를 insert

    @JoinColumn(name = "userId") // DB컬럼명 설정
    @ManyToOne
    private User user;

    //이미지 좋아요 정보

    //댓글

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
