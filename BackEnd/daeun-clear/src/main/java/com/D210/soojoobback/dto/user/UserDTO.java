package com.D210.soojoobback.dto.user;

import com.D210.soojoobback.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Builder
    public UserDTO(Long id, String username, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UserDTO(User user){
        this.id = id;
        this.username = username;
        this.email =email;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
