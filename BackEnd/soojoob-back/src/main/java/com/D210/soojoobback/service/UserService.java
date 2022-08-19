package com.D210.soojoobback.service;

import com.D210.soojoobback.dto.user.*;
import com.D210.soojoobback.entity.Record;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.RecordRepository;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RecordRepository recordRepository;


    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() ->  new CustomErrorException("해당 아이디가 없습니다."));
        return new UserDTO(user);
    }

    @Transactional
    public User save(SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String role = "ROLE_USER";
        Optional<User> usernameFound = userRepository.findOByUsername(username);
        Optional<User> emailFound = userRepository.findByEmail(email);


        if (emailFound.isPresent()) {
            throw new CustomErrorException("중복된 이메일 입니다 ");
        } else if (!isValidEmail(email)) {
            throw new CustomErrorException("이메일 형식이 올바르지 않습니다");
        } else if (usernameFound.isPresent()) {
            throw new CustomErrorException("중복된 닉네임 입니다 ");
        } else if (password.length() < 4 || password.length() > 12) {
            throw new CustomErrorException("비밀번호를 6자 이상  12자 이하로 입력하세요");
        } else if (password.contains(email)) {
            throw new CustomErrorException("패스워드는 아이디를 포함할 수 없습니다.");
        }

        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        User user = new User(username, password, email, role);

        User user1 = userRepository.save(user);
        Record record = new Record(user1);
        recordRepository.save(record);
        return user;
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) { err = true; } return err;
    }

    public User login(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(
                () -> new CustomErrorException("이메일을 찾을 수 없습니다")
        );
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomErrorException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }

    public LoginDetailResponseDto toSetLoginDetailResponse(User user) {

        return user.toBuildDetailUser();
    }

    public boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomErrorException("이메일을 찾을 수 없습니다")
        );
        userRepository.delete(user);
        return true;
    }

    public boolean nicknameCheck(String username) {
        Optional<User> usernameFound = userRepository.findOByUsername(username);

        if (usernameFound.isPresent()) {
            throw new CustomErrorException("중복된 닉네임 입니다 ");
        }
        return true;
    }

    //이메일 중복 검사
    public boolean emailCheck(String email) {
        Optional<User> emailFound = userRepository.findByEmail(email);

        if (emailFound.isPresent()) {
            throw new CustomErrorException("중복된 이메일 입니다 ");
        }
        return true;
    }

    public UserInfoDetailsDto detailsUserInfo(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        UserInfoDetailsDto userInfoDetailsDto = new UserInfoDetailsDto(user);

        return userInfoDetailsDto;
    }

    public User userFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) userDetails).getUser();
        } else {
            throw new CustomErrorException("로그인이 필요합니다.");
        }
    }

    //회원 정보 수정
    public EditUserResponseDto editUserInfo(EditUserRequestDto editUserInfoDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String username = editUserInfoDto.getUsername();
        String email = editUserInfoDto.getEmail();


        Integer age = editUserInfoDto.getAge();

        Integer weight = editUserInfoDto.getWeight();

        Integer height = editUserInfoDto.getHeight();

        String region = editUserInfoDto.getRegion();




        user.setUsername(username);
        user.setEmail(email);
        user.setAge(age);
        user.setWeight(weight);
        user.setHeight(height);
        user.setRegion(region);

        userRepository.save(user);

        return new EditUserResponseDto(user);
    }

    public void editUserPassword(EditPasswordRequestDto editPasswordInfoDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        String password;
        if (user.getPassword().equals(editPasswordInfoDto.getPassword())) {
            password = user.getPassword();
        } else {
            password = editPasswordInfoDto.getPassword();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
