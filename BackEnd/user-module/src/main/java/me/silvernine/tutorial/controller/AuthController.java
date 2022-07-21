package me.silvernine.tutorial.controller;

import me.silvernine.tutorial.dto.LoginDto;
import me.silvernine.tutorial.dto.TokenDto;
import me.silvernine.tutorial.jwt.JwtFilter;
import me.silvernine.tutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

//    로그인 API 경로
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        // 만들어준 LiginDto의 정보 파라미터로 받고 토큰객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticate 메소드가 실행이 될때 CustomUserDetailsService에서 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // Authentication객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT토큰생성(tokenPrivider에서 만들었던 createToken 메소드)
        String jwt = tokenProvider.createToken(authentication);
        // Response Header에 토큰 넣기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // Response Body에 토큰 넣기
        // 만들어둔 TokenDto 활용
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
