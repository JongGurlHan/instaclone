package com.jghan.instaclone.handler.aop;

import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {


    //@Before(): 특정 함수 실행직전에 실행
    //@After(): 특정 함수 실행 직후에 실행
    //@Around(): 특정 함수 실행직전부터 직후에 관여
    //execution(): 접근 제어자(public, protected..) 설정  * : 다 지정
    //Controller의 모든 메소드의 파라미터가 뭐든 상관없다 (모든 컨트롤러의 메소드가 실행할때 작동한다.)
    //api의 Controller중에 특정 함수가 실행됐으면,  proceedingJoinPoint 매개변수 적어놓으면 proceedingJoinPoint가
    //그 함수의 매개변수 뿐만 아니라 내부의 모든 정보에 접근할 수 있다.
    @Around("execution(* com.jghan.instaclone.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //proceedingJoinPoint=> profile 함수의 모든곳에 접근할 수 있는 변수
        //profile 함수보다 먼저 실행

//        System.out.println("==================web api 컨트롤러===================");

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg: args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);

                }

            }
        }
        return proceedingJoinPoint.proceed(); //prfofile함수가 실행됨
    }

    @Around("execution(* com.jghan.instaclone.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


//        System.out.println("==================web 컨트롤러===================");

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg: args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());

                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }

            }
        }

        return proceedingJoinPoint.proceed();


    }
}
