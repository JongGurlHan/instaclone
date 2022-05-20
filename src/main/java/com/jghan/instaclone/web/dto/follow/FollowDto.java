package com.jghan.instaclone.web.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//follow정보를 보는 dto
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowDto {

    //팔로우(언팔)할 대상의 아이디
    private int id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState; //팔로우 여부
    private Integer equalUserState; //로그인한 유저와 팔로우중인 유저가 같은지


}
