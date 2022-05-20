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

    //select하는거기 때문에 @Modifying 필요 없음
    //구독 여부(test1으로 로그인, test2 페이지로감 - 1번이 2번 팔로우 했는지 - 1나오면 구독한다는거 ), 1이면 팔로우한 상태
    @Query(value = "SELECT COUNT(*) FROM follow WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mFollowState(int principalId, int pageUserId);

    //해당 페이지의 유저가 팔로우 하는 수
    @Query(value = "SELECT COUNT(*) FROM follow WHERE fromUserId = :pageUserId", nativeQuery = true)
    int mFollowCount(int pageUserId);

}
