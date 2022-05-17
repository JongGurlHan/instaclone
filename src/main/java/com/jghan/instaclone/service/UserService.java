package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import com.jghan.instaclone.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User update(int id, User user){
        //1.영속화
        //1) get() :무조건 찾았다. 걱정마
        //2) orElseThrow() :못찾았어 exception발동시킬께

        User userEntity = userRepository.findById(id).orElseThrow(() -> {return new CustomValidationApiException("찾을수 없는id입니다."); });

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        //2.영속화된 오브젝를 수정 - 더티체킹(업데이트 완료)
        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }   //더테체킹이 일어나서 업데이트가 완료됨.
}
