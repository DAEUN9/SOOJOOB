package com.D210.soojoobback.dto.user;

import com.D210.soojoobback.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserResponseDto {
    private Long id;

    private String username;

    private String email;

    private Integer age;

    private String gender;

    private String region;

    private Integer weight;

    private Integer height;

    public EditUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.weight = user.getWeight();
        this.height = user.getHeight();
        this.gender = user.getGender();
        this.region = user.getRegion();
    }
}