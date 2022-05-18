package com.jghan.instaclone.web.dto.image;

import com.jghan.instaclone.domain.image.Image;
import com.jghan.instaclone.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {


    //MultipartFile 에는 @Notblank가 지원안된다.
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImagUrl, User user){
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImagUrl)
                .user(user)
                .build();
    }
}
