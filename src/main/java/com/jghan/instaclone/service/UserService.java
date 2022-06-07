package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.follow.FollowRepositoy;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import com.jghan.instaclone.handler.ex.CustomApiException;
import com.jghan.instaclone.handler.ex.CustomException;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import com.jghan.instaclone.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepositoy followRepositoy;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}") //application.properties에서 가져옴
    private String uploadFolder;

    @Transactional
    public User profileImageUrlUpdate(int principalId, MultipartFile profileImageFile){

        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            // throw -> return 으로 변경
            return new CustomApiException("유저를 찾을 수 없습니다.");
        });

        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }





    @Transactional(readOnly = true)
    public UserProfileDto userProfile(int pageUserId, int principalId){

        UserProfileDto dto = new UserProfileDto();

        //SELECT * FROM image WHERE USERiD = :userId;
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        }); //유저를 못찾을수 있으니 orElseThrow던짐

        dto.setUser(userEntity);
        //pageUserId 와 principalId가 같은지 비교
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int followState = followRepositoy.mFollowState(principalId, pageUserId);
        int followCount = followRepositoy.mFollowCount(pageUserId);

        dto.setFollowState(followState == 1);
        dto.setFollowCount(followCount);

        //좋아요 카운트 추가하기 - dto내부의 userEntity 수정
        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });


        return dto;
    }

    @Transactional
    public User update(int id, User user){
        //1.영속화
        //1) get() :무조건 찾았다. 걱정마
        //2) orElseThrow() :못찾았어 exception발동시킬께

        User userEntity = userRepository.findById(id).orElseThrow(() -> { return new CustomValidationApiException("찾을 수 없는 id입니다.");});

        //2.영속화된 오브젝를 수정 - 더티체킹(업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }   //더테체킹이 일어나서 업데이트가 완료됨.
}
