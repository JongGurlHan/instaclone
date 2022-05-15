package com.jghan.instaclone.service;

import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //1 IOC 2.트랜젝션 관리
public class AuthService {

    private final UserRepository userRepository;

    public User register(User user){
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
