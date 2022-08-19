package me.silvernine.tutorial.dto;

import lombok.*;

//토큰정보를 response할때 사용
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}
