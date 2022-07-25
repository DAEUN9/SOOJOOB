package me.silvernine.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.silvernine.tutorial.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

//회원가입시 사용
public class UserDto {

   @NotNull
   @Size(min = 3, max = 50)
   private String username;

   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   @NotNull
   @Size(min = 3, max = 100)
   private String password;

   @NotNull
   @Size(min = 3, max = 50)
   private String nickname;

   @NotNull
   @Size(min = 3, max = 50)
   private String email;

   @NotNull
   private Integer age;

   @NotNull
   @Size(min = 1, max = 1)
   private String gender;

   @NotNull
   @Size(min = 3, max = 50)
   private String region;

   @NotNull
   private Integer weight;

   @NotNull
   private Integer height;


   private Set<AuthorityDto> authorityDtoSet;

   public static UserDto from(User user) {
      if(user == null) return null;

      return UserDto.builder()
              .username(user.getUsername())
              .nickname(user.getNickname())
              .email(user.getEmail())
              .age(user.getAge())
              .gender(user.getGender())
              .region(user.getRegion())
              .weight(user.getWeight())
              .height(user.getHeight())
              .authorityDtoSet(user.getAuthorities().stream()
                      .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                      .collect(Collectors.toSet()))
              .build();
   }
}