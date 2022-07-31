package com.D210.soojoobback.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDetailResponseDto {
    private Long id;

    private String username;

    private String email;

    private String role;

    private String gender;

    private Integer age;

    private Integer weight;

    private Integer height;

    private String region;

}
