package com.D210.soojoobback.dto.user;

import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.security.UserDetailsImpl;
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

    @Builder
    public UserDTO(Long id, String username, String email){
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }


    public User toEntity(){
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}
