package com.D210.soojoobback.controller;


import com.D210.soojoobback.JwtTokenProvider;
import com.D210.soojoobback.UserDetailsImpl;
//import com.D210.soojoobback.config.auth.PrincipalDetails;
import com.D210.soojoobback.dto.user.*;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
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


//	@GetMapping({ "", "/" })
//	public @ResponseBody String index() {
//		return "인덱스 페이지입니다.";
//	}
////
////	@GetMapping("/user")
////	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
////		System.out.println("Principal : " + principal);
////		System.out.println("OAuth2 : "+principal.getUser().getProvider());
////		// iterator 순차 출력 해보기
////		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
////		while (iter.hasNext()) {
////			GrantedAuthority auth = iter.next();
////			System.out.println(auth.getAuthority());
////		}
////
////		return "유저 페이지입니다.";
////	}
//	// template의 로그인페이지로 감
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//
//	@GetMapping("/join")
//	public String join() {
//		return "join";
//	}
//
//	@GetMapping("/admin")
//	public @ResponseBody String admin() {
//		return "어드민 페이지입니다.";
//	}
//
//	//@PostAuthorize("hasRole('ROLE_MANAGER')")
//	//@PreAuthorize("hasRole('ROLE_MANAGER')")
//	@Secured("ROLE_MANAGER")
//	@GetMapping("/manager")
//	public @ResponseBody String manager() {
//		return "매니저 페이지입니다.";
//	}



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
		List<UserInfoDetailsDto> userInfoDetailsDto = userService.detailsUserInfo(userDetails);

		return new ResponseDto(200L, "회원 정보를 전송했습니다", userInfoDetailsDto);

	}

//	@GetMapping("/users")
//	@ResponseBody
//	public ResponseDto userInfoDetails(
//			HttpServletRequest request
//	) {
//		System.out.println(request);
//		String token = request.getHeader("X-AUTH-TOKEN");
//		System.out.println(
//				token
//		);
//		if (jwtTokenProvider.validateToken(token)) {
//			Long userId = jwtTokenProvider.getUserIdFromJwt(token);
//
//			// 토큰 유효성 검사를 거쳤기 때문에 무조건 정보가 존재한다.
//			UserDTO userDto = userService.findById(userId);
//			return new ResponseDto(200L, "회원 정보를 전송했습니다", userDto);
//		} else {
//			return new ResponseDto(500L, "회원조회 실패", "");
//		}
//	}

//	@GetMapping("/{userId}/info")
//	public ResponseDto getUserInfo(@PathVariable Long userId)
//		throws CustomErrorException {
//		Long id = jwtTokenProvider.getUserIdx();
//		UserDTO userDTO = userService.findById(userId);
//		return new ResponseDto(200L, "회원정보 전송", userDTO);
//	}

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

	@GetMapping("/nickname-check")
	public ResponseDto nickNameCheck(
			String username
	) {
		userService.nicknameCheck(username);
		return new ResponseDto(200L, "사용 가능한 닉네임입니다 !", "");

	}


	@GetMapping("/email-check")
	public ResponseDto emailCheck(
		String email
	) {
		userService.emailCheck(email);
		return new ResponseDto(200L, "사용 가능한 이메일입니다 !", "");

	}

//	@PostMapping("/user")
//	public String save(@RequestBody UserDTO userDTO){
//		return userService.save(userDTO);
//	}

//	@PutMapping("/user/{id}")
//	public Long update(@PathVariable Long id, @RequestBody UserDTO userDTO){
//		return userService.update(id, userDTO);
//	}
//

	// id(pk)로 유저정보 조회
	@GetMapping("/{id}")
	public ResponseDto findById(@PathVariable Long id){

		UserDTO userDTO = userService.findById(id);
		return new ResponseDto(200L, "ID에 해당하는 유저정보를 전송했습니다 !", userDTO);
	}
}
