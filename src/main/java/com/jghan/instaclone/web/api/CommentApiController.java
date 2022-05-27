package com.jghan.instaclone.web.api;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.comment.Comment;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.service.CommentService;
import com.jghan.instaclone.web.dto.CMRespDto;
import com.jghan.instaclone.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails){

        Comment comment = commentService.commentSave(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); //content, imageId, userId
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
    }

    //CommentDto commentDto만 쓰는건 x-www-urlencoded 로 받는거기 때문에
    //json 데이터 받으려면  @RequestBody  붙여줘야한다.

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){

        commentService.commentDelete(id);

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제", null), HttpStatus.OK);
    }
}
