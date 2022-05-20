package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.follow.FollowRepositoy;
import com.jghan.instaclone.handler.ex.CustomApiException;
import com.jghan.instaclone.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepositoy followRepositoy;

    @Transactional(readOnly = true)
    public List<FollowDto> followList(int principalId, int pageUserId){

        return  null;

    }

    @Transactional
    public void follow(int fromUserId, int toUserId){
        try {
            followRepositoy.mFollow(fromUserId,toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 팔로우 했습니다.");
        }
    }


    @Transactional
    public void unfollow(int fromUserId, int toUserId){
        followRepositoy.mUnFollow(fromUserId,toUserId);
    }
}
