package com.jghan.instaclone.web.api;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.service.FollowService;
import com.jghan.instaclone.service.UserService;
import com.jghan.instaclone.web.dto.CMRespDto;
import com.jghan.instaclone.web.dto.follow.FollowDto;
import com.jghan.instaclone.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final FollowService followService;


    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.profileImageUrlUpdate(principalId, profileImageFile);
        principalDetails.setUser(userEntity); // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
    }


    //페이지 주인이 팔로하고 있는 모든 정보를 get함
    @GetMapping("api/user/{pageUserId}/follow")
    public ResponseEntity<?>followList(@PathVariable int pageUserId,  @AuthenticationPrincipal PrincipalDetails principalDetails){

        List<FollowDto> followDto = followService.followList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "팔로우 정보 리스트 가져오기 성공", followDto), HttpStatus.OK);
    }


    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라미터에 적어야됨.
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

            User userEntity = userService.update(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //수정된 값에 따라 세션정보 변경
            return new CMRespDto<>(1, "회원수정완료", userEntity); //응답시에 userEntity의 모든 getter함수가 호출되고 JSON으로 파싱하여 응답한다.



    }


}
