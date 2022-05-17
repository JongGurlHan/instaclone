package com.jghan.instaclone.web.api;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.handler.ex.CustomValidationException;
import com.jghan.instaclone.service.UserService;
import com.jghan.instaclone.web.dto.CMRespDto;
import com.jghan.instaclone.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라미터에 적어야됨.
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                            }
            throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
        }else {

            User userEntity = userService.update(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //수정된 값에 따라 세션정보 변경
            return new CMRespDto<>(1, "회원수정완료", userEntity);


        }



    }


}
