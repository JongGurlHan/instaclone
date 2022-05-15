package com.jghan.instaclone.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HttpBodyController {


    @PostMapping("/body1")
    public String xwwformurlencoded(String username){
        //스프링은 기본적으로 x-www-formurlencoded 타입을 파싱(분석)해준다
        log.info(username);
        return  "key=value 전송옴";
    }

    @PostMapping("/body2")
    public String plaintext(@RequestBody String data){ //평문
        log.info(data);
        return  "plain/text 전송옴";
    }

    @PostMapping("/body3")
    public String applicationjson(@RequestBody String data){
        log.info(data);
        return  "json 전송옴";
    }
    @PostMapping("/body4")
    public String applicationjsonToObject(@RequestBody User user){ //json데이터를 자바object로 받음
        log.info(user.getUsername());
        return  "json 전송옴";
    }

}
