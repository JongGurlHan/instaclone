package com.jghan.instaclone.web;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping({"/user/{id}"})
    public String profile(@PathVariable int id){
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
