package com.jghan.instaclone.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //Modifying queries can only use void or int/Integer as return type!
    //@Modifying 쿼리는  void/int/Integer 형만 리턴할 수 있기에 Comment 객체로 받을 수 없다.
    @Modifying
    @Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery = true)
    Comment mSave(String content, int imageId, int userId);
}
