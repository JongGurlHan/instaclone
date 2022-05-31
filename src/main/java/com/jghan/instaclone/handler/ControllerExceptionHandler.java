package com.jghan.instaclone.handler;

import com.jghan.instaclone.handler.ex.CustomApiException;
import com.jghan.instaclone.handler.ex.CustomException;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.handler.ex.CustomValidationException;
import com.jghan.instaclone.util.Script;
import com.jghan.instaclone.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice //모든 Exception들을 낚아챔
public class ControllerExceptionHandler {

    //js리턴 - client한테 응답
    @ExceptionHandler(CustomValidationException.class) //CustomValidationException 발동하는 모든 Exception을 이 함수가 가로챔
    public String validationException(CustomValidationException e){
        if(e.getErrorMap() ==null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){
       return  Script.back(e.getMessage());
    }

    //CMRespDto는 전역적으로 쓸거라서 제네릭으로 선언
    //object리턴 - api(ajax)통신때
    //ajax통신할때는 ResponseEntity를 사용해야지 http 상태코드를 전달할 수 있다. 그래서 분기하기가 쉽다.
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
