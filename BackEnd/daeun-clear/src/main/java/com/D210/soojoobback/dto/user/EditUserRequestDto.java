package com.D210.soojoobback.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDto {

    private String username;

    private String password;

    private String email;

    private Integer age;

    private String gender;

    private String region;

    private Integer weight;

    private  Integer height;
}
