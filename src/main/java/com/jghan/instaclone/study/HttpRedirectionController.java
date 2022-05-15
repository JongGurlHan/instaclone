package com.jghan.instaclone.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HttpRedirectionController {


    @GetMapping("/home")
    public String home(){
        //1만줄 가정
        return "home";
    }

    @GetMapping("/away")
    public String away(){
        //다른 코드
        //1만줄 가정
        return  "redirect:/home"; //리다이렉션이 된다 , @Controller일때만 된다.
    }


}
