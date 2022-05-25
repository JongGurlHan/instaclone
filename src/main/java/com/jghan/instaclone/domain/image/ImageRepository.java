package com.jghan.instaclone.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Integer> {

    //로그인 한 사람이 자신을 제외한 다른 유저들의 사진을 구하는 쿼리
    //페이지로 리턴되는까 반환타입 Page
    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM follow WHERE fromUserId = :principalId) ORDER BY id DESC", nativeQuery = true)
    List<Image> mStory(int principalId);

    @Query(value = "    SELECT i.* FROM image i INNER JOIN(SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC", nativeQuery = true)
    List<Image> mPopular();


}
