package com.D210.soojoobback.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private LoginDetailResponseDto user;
    private String jwtToken;
}

