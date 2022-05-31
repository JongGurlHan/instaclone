package com.jghan.instaclone.config.auth;

import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //.loginProcessingUrl("/auth/signin") 가 있으면 로그인 요청으로 인식하고
    //원래라면 UserDetailsService가 낚아채야하지만 PrincipalDetailsService가 UserDetailsService를 상속 받기때문에 여기서 대신 낚아채서 로그인 실행

    //1. 패스워드는 알아서 체킹하니가 신경x
    //2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만든다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            return null;
        }else{
            //PrincipalDetails 은 UserDetails을 상속
            return new PrincipalDetails(userEntity);
        }

    }
}
