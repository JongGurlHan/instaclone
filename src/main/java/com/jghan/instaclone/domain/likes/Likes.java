package com.jghan.instaclone.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jghan.instaclone.domain.image.Image;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"} //한 유저가 특정 이미지를 중복으로 like할 수 없기때문에
                )
        }
)
public class Likes { //N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name="imageId")
    @ManyToOne //기본 패치전략은 EAGER
    private Image image; // 1

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name="userId")
    @ManyToOne
    private User user; //1

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
