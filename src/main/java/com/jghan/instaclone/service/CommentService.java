package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.comment.Comment;
import com.jghan.instaclone.domain.comment.CommentRepository;
import com.jghan.instaclone.domain.image.Image;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import com.jghan.instaclone.handler.ex.CustomApiException;
import com.jghan.instaclone.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment commentSave(String content, int imageId, int userId){

        //* 객체를 만들때 id 값만 담아서 insert할 수 있다.
        //대신 return시에 image객체는 id값만 가지고 있는 빈 객체를 리턴받는다.
        Image image = new Image();
        image.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            // throw -> return 으로 변경
            return new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);

        comment.setImage(image);
        comment.setUser(userEntity);


        return commentRepository.save(comment);

    }

    @Transactional
    public void commentDelete(int  id){
        try{
            commentRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }

    }

}
