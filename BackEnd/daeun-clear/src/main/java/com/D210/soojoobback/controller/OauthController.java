package com.D210.soojoobback.controller;


import com.D210.soojoobback.dto.user.LoginDetailResponseDto;
import com.D210.soojoobback.dto.user.LoginResponseDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.GoogleUser;
import com.D210.soojoobback.entity.OAuthUserInfo;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.security.JwtTokenProvider;
import com.D210.soojoobback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final UserRepository userRepository;

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/oauth/jwt/google")
    public ResponseDto jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate 실행됨");
        OAuthUserInfo googleUser =
                new GoogleUser((Map<String, Object>)data);

        User userEntity =
                userRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());

        if(userEntity == null) {
            User userRequest = User.builder()
                    .username(googleUser.getProvider()+"_"+googleUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("skldjficmnwl123kclknmcnfklsdkskmxxfofohue"))
                    .email(googleUser.getEmail())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .role("ROLE_USER")
                    .build();

            userEntity = userRepository.save(userRequest);
        }

        String jwtToken = jwtTokenProvider.createToken(userEntity.getEmail());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        LoginDetailResponseDto loginDetailResponseDto = userService.toSetLoginDetailResponse(userEntity);
        loginResponseDto.setUser(loginDetailResponseDto);
        loginResponseDto.setJwtToken(jwtToken);

        return new ResponseDto(200L, "구글 로그인에 성공했습니다", loginResponseDto);
//        return jwtToken;
    }
}
