package com.jghan.instaclone.web.api;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.image.Image;
import com.jghan.instaclone.service.ImageService;
import com.jghan.instaclone.service.LikesService;
import com.jghan.instaclone.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?>imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        List<Image> images = imageService.imageStory(principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }

    //이미지 좋아요
    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?>likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        likesService.like(imageId, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요성공", null), HttpStatus.CREATED);

    }

    //이미지 좋아요 취소
    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?>unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        likesService.unlike(imageId, principalDetails.getUser().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소성공", null), HttpStatus.OK);

    }

    //이미지 좋아요
    @DeleteMapping("/api/image/{imageId}/delete")
    public ResponseEntity<?>delete(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        imageService.delete(imageId);

        return new ResponseEntity<>(new CMRespDto<>(1, "삭제성공", null), HttpStatus.OK);

    }

}
