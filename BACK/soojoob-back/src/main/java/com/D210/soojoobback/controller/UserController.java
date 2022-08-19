package com.D210.soojoobback.controller;



import com.D210.soojoobback.dto.user.*;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.security.JwtTokenProvider;
import com.D210.soojoobback.security.UserDetailsImpl;
import com.D210.soojoobback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;
	@Autowired
	private UserRepository userRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;




	@PostMapping("")
	@ResponseBody
	public ResponseDto createUser(
			@RequestBody SignupRequestDto signupRequestDto) {
		if (userService.save(signupRequestDto) != null) {
			String username = signupRequestDto.getUsername();
			log.info(username + "님 환영합니다 !");
			return new ResponseDto(201L, "회원가입에 성공하였습니다 !", "");
		}
		return new ResponseDto(500L, "회원가입에 실패하였습니다 ...", "");
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseDto login(
			@RequestBody LoginUserDto loginUserDto,
			HttpServletResponse response) {
		User user = userService.login(loginUserDto);
		String checkEmail = user.getEmail();

		String token = jwtTokenProvider.createToken(checkEmail);
		response.setHeader("X-AUTH-TOKEN", token);

		Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		response.addCookie(cookie);

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		LoginDetailResponseDto loginDetailResponseDto = userService.toSetLoginDetailResponse(user);
		loginResponseDto.setUser(loginDetailResponseDto);
		loginResponseDto.setJwtToken(token);

		return new ResponseDto(200L, "로그인에 성공했습니다", loginResponseDto);
	}

	@DeleteMapping("")
	@ResponseBody
	public ResponseDto deleteUser(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		checkLogin(userDetails);
		String email1 = userDetails.getUser().getEmail();
		if (userService.deleteUser(email1)) {
			return new ResponseDto(204L, "회원탈퇴하였습니다", "");
		}
		return new ResponseDto(500L, "회원삭제 실패", "");
	}

	@GetMapping("")
	@ResponseBody
	public ResponseDto userInfoDetails(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		checkLogin(userDetails);
		UserInfoDetailsDto userInfoDetailsDto = userService.detailsUserInfo(userDetails);

		return new ResponseDto(200L, "회원 정보를 전송했습니다", userInfoDetailsDto);

	}


	@PutMapping("/update")
	@ResponseBody
	public ResponseDto editUserInfo(
			 @RequestBody EditUserRequestDto editUserInfoDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		checkLogin(userDetails);
		EditUserResponseDto editUserResponseDto = userService.editUserInfo(editUserInfoDto, userDetails);

		return new ResponseDto(200L, "회원 정보를 수정했습니다", editUserResponseDto);

	}

	@PutMapping("/change/pw")
	@ResponseBody
	public ResponseDto editUserPassword(
			@RequestBody EditPasswordRequestDto editPasswordRequestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		checkLogin(userDetails);
		userService.editUserPassword(editPasswordRequestDto, userDetails);

		return new ResponseDto(200L, "비밀 번호를 수정했습니다", "");
	}


	private void checkLogin(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		System.out.println(userDetails);
		if (userDetails == null) {
			throw new CustomErrorException("로그인이 필요합니다.2");
		}
	}

	@GetMapping("/nickname-check/{username}")
	public ResponseDto nickNameCheck(
			@PathVariable("username") String username
	) {
		userService.nicknameCheck(username);
		return new ResponseDto(200L, "사용 가능한 닉네임입니다 !", "");

	}


	@GetMapping("/email-check/{email}")
	public ResponseDto emailCheck(
			@PathVariable("email") String email
	) {
		userService.emailCheck(email);
		return new ResponseDto(200L, "사용 가능한 이메일입니다 !", "");

	}



	// id(pk)로 유저정보 조회
	@GetMapping("/{id}")
	public ResponseDto findById(@PathVariable Long id){

		UserDTO userDTO = userService.findById(id);
		return new ResponseDto(200L, "ID에 해당하는 유저정보를 전송했습니다 !", userDTO);
	}


}
