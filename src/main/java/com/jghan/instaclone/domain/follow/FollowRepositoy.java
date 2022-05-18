package com.jghan.instaclone.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepositoy extends JpaRepository<Follow,Integer>{

    @Modifying //INSERT, DELETE, UPDATE를 네이티브쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "INSERT INTO follow(fromUserId, toUserId, createDate) Values(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mFollow(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnFollow(int fromUserId, int toUserId);
}
