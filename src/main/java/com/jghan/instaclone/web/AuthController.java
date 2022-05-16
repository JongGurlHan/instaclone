package com.jghan.instaclone.web;

import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.handler.ex.CustomValidationException;
import com.jghan.instaclone.service.AuthService;
import com.jghan.instaclone.web.dto.auth.SignupDto;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final 필드를 DI할때 사용
@Controller //1.IOC 2.파일을 리턴하는 컨트롤러
@Slf4j
public class AuthController {

//    @Autowired
//    private AuthService authService;

    private final AuthService authService;

//    public AuthController(AuthService authService){
//        this.authService = authService;
//    }

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { //key=value (x-www-form-urlencoded)

        //프론트 단에서 유효성 검사해도 postman 전송할 수 있으니까
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());

            }
            throw new CustomValidationException("유효성 검사 실패함", errorMap);
        } else {
            log.info(signupDto.toString());

            //User < - SingupDto
            User user = signupDto.toEntity();
            User userEntity = authService.register(user);
            System.out.println(userEntity);
            log.info(user.toString());

            return "auth/signin";
        }


    }

}
