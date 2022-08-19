package com.D210.soojoobback.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {

    private String email;

    private String username;

    private String password;

}
