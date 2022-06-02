package com.jghan.instaclone.domain.follow;

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
                        name = "follow_uk", //제약조건 이름
                        columnNames = {"fromUserId", "toUserId"} //실제 db이 컬럼명이 들어가야한다.
                )
        }
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId") // DB컬럼명 설정
    @ManyToOne
    private User fromUser; //팔로우 하는애

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser; //팔로우 받는애

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

}

