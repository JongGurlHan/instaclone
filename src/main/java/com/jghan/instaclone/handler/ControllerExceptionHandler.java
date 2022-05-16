package com.jghan.instaclone.handler;

import com.jghan.instaclone.handler.ex.CustomValidationException;
import com.jghan.instaclone.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //모든 Exception들을 낚아챔
public class ControllerExceptionHandler {


    //CMRespDto는 전역적으로 쓸거라서 제네릭으로 선언
    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 발동하는 모든 Exception을 이 함수가 가로챔
    public CMRespDto<?> validationException(CustomValidationException e){
        return new CMRespDto<Map<String, String >>(-1, e.getMessage(), e.getErrorMap());
    }
    //AuthController에서 터진 Exception을  validationException이 낚아채서(실행해서) e.getMessage();를 리턴함
}
