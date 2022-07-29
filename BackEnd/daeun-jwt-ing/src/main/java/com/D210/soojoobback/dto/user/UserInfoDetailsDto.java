package com.D210.soojoobback.dto.user;

import com.D210.soojoobback.entity.User;

public class UserInfoDetailsDto {
    private Long userid;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private String gender;
    private String region;
    private Integer weight;
    private Integer height;


    public UserInfoDetailsDto(User user) {
        this.userid = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.region = user.getRegion();
        this.weight = user.getWeight();
        this.height = user.getHeight();

    }
}
