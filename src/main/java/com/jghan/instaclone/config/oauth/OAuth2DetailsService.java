package com.jghan.instaclone.config.oauth;

import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.user.User;
import com.jghan.instaclone.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

//로그인 했을때 페북으로부터 인증됐을때 응답이 오는데, 이 응답을 처리하는 역할은 한다.
//여기서 응답은 회원정보(email, public_profile)이다.
@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("OAuth2 서비스 탐");
        OAuth2User oAuth2User = super.loadUser(userRequest); //응답정보를 파싱해서 회원정보를 넣어준다.
//        System.out.println(oAuth2User.getAttributes());

        Map<String, Object> userinfo = oAuth2User.getAttributes();

        String username = "facebook_"+(String) userinfo.get("id");
        String password = new BCryptPasswordEncoder( ).encode(UUID.randomUUID().toString());
        String name = (String) userinfo.get("name");
        String email = (String) userinfo.get("email");
        //어차피 oauth는 username, password적어서 로그인하는거가 아니기 때문에 중복되지 않은 값만 넣어주도록 한다.


        //한번 oauth로 로그인으로 회원가입 진행해서 유저정보 저장됐다.
        // -> 근데 그 다음에 다시 페이스북 로그인하면 다시 한번 회원가입이 된다.
        // 그래서 회원 정보가 있는지 있는지 확인해야한다.
        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){ //페이스북 최초 로그인
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .email(email)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());
        }else{ //이전에 페이스북 로그인
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes()) ;
        }
    }
}