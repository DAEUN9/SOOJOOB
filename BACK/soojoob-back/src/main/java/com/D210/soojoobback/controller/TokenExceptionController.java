package com.D210.soojoobback.controller;

import com.D210.soojoobback.exception.NoTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TokenExceptionController {
    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new NoTokenException("로그인이 필요합니다.");
    }
}
