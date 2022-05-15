package com.jghan.instaclone.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//12강 Controller - Json응답하기
@RestController
@Slf4j
public class HttpResponseJsonController {


    @GetMapping("/resp/json")
    public String respJson(String username){
        return  "{\"username\" : \"james\"}";
    }
    @GetMapping("/resp/json/object")
    public User respJsonObject(String username){
        User user = new User();
        user.setUsername("홍길동");

//        String data = "{\"username\" :\"" + user.getUsername()+"\"}";
//        return data;
        // 레가시에선 user정보를 json형태로 보내려면 이렇게 해야했지만 스프링부트에서는
        return  user;
        //1. MessageConverter가 자동으로 JavaObject를 Json으로 변경해서 통신을 통해 응답해준다.
        //2. @RestController 일때만 MessageConverter가 작동한다다
    }
}
