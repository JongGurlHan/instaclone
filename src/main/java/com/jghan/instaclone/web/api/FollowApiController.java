package com.jghan.instaclone.web.api;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.service.FollowService;
import com.jghan.instaclone.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowApiController {

    private final FollowService followService;

    //누가 구독하는지 - 로그인 유저 정보에서 가져와서 쓴다.
    @PostMapping("/api/follow/{toUserId}")
    public ResponseEntity<?> follow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){

        followService.follow(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "팔로우성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/follow/{toUserId}")
    public ResponseEntity<?> unfollow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){

        followService.unfollow(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "언팔로우 성공", null), HttpStatus.OK);
    }
}

