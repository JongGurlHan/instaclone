package com.jghan.instaclone.web;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.service.UserService;
import com.jghan.instaclone.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping({"/user/{pageUserId}"})
    public String profile(@PathVariable int pageUserId, Model model,
                          @AuthenticationPrincipal PrincipalDetails principalDetails){

        UserProfileDto dto = userService.userProfile(pageUserId, principalDetails.getUser().getId());

        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping({"/user/{id}/update"})
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
//        System.out.println("세션정보"+ principalDetails.getUser());

        //principal: 인증주체, 인증된 사람의 오브젝트명
        model.addAttribute("principal", principalDetails.getUser());
        return "user/update";
    }
}
