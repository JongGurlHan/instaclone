package com.jghan.instaclone.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jghan.instaclone.domain.likes.Likes;
import com.jghan.instaclone.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private String postImageUrl; //-Db에 그 저장될 이미지 명

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId") // DB컬럼명 설정
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    //이미지 좋아요 정보
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image") //lazy가 기본
    private List<Likes>likes;
    //댓글

    private LocalDateTime createDate;

    @Transient //DB에 컬럼이 만들어지지 않는다.
    private boolean likeState;

    @Transient
    private int likeCount;



    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

// 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User부분을 출력되지 않게 함(무한참조 방지위해서)
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                ", createDate=" + createDate +
//                '}';
//    }
}
