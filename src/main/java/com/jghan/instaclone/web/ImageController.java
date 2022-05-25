package com.jghan.instaclone.web;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.image.Image;
import com.jghan.instaclone.handler.ex.CustomValidationException;
import com.jghan.instaclone.service.ImageService;
import com.jghan.instaclone.service.UserService;
import com.jghan.instaclone.web.dto.image.ImageUploadDto;
import com.jghan.instaclone.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;


    @GetMapping({"/", "/image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping({"/image/popular"})
    public String popular(Model model){

        List<Image> images =imageService.popularImg();
        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping({"/image/upload"})
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        imageService.upload(imageUploadDto, principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId();
    }


}
