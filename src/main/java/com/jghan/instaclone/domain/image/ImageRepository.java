package com.jghan.instaclone.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    //로그인 한 사람이 자신을 제외한 다른 유저들의 사진을 구하는 쿼리
    @Query(value = "SELECT * FROM image WHERE userId \n" +
            "IN (SELECT toUserId FROM follow WHERE fromUserId = :principalId);", nativeQuery = true)
    List<Image> mStory(int principalId);

}
