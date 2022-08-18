package com.D210.soojoobback.controller;


import com.D210.soojoobback.dto.badge.BadgeListResDto;
import com.D210.soojoobback.dto.plogging.PloggingInfoDto;
import com.D210.soojoobback.dto.plogging.PostPloggingReqDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.PloggingRepository;
import com.D210.soojoobback.security.JwtTokenProvider;
import com.D210.soojoobback.security.UserDetailsImpl;
import com.D210.soojoobback.service.PloggingService;
import com.D210.soojoobback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/plogging")
public class PloggingController {

    private final PloggingService ploggingService;
    @Autowired
    private PloggingRepository ploggingRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;


    @PostMapping("")
    public ResponseDto createPlogging(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody PostPloggingReqDto requestDto
    ) {
        checkLogin(userDetailsImpl);
        User user = userDetailsImpl.getUser();
        List<BadgeListResDto> data = ploggingService.save(requestDto, user);
        return new ResponseDto(201L,"플로깅 생성에 성공했습니다 !", data);
    }

    private void checkLogin(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails);
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.2");
        }
    }


    @DeleteMapping("/delete/{plogging_id}")
    public ResponseDto deletePlogging(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("plogging_id") Long ploggingId
    ) {
        checkLogin(userDetails);
        User user = userDetails.getUser();
        ploggingService.deletePlogging(ploggingId,user);
        return new ResponseDto(204L, "플로깅 기록 삭제에 성공하였습니다.", "");
    }

    @GetMapping("/{plogging_id}")
    public ResponseDto getDetailPlogging(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("plogging_id") Long ploggingId
    ) {
        PloggingInfoDto responseDto = ploggingService.getDetailPloggingById(ploggingId);

        return new ResponseDto(200L,"플로깅 상세정보 불러오기에 성공하였습니다.", responseDto);

    }

    // 현재 유저 플로깅 리스트
    @GetMapping("/all")
    public ResponseDto currUserPloggings(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        User user = userDetails.getUser();
        List<PloggingInfoDto> data = ploggingService.getMyPloggingListByUser(user);
        return new ResponseDto(200L,"내 플로깅 불러오기 성공", data);

    }

    // 회원별 플로깅 리스트
    @GetMapping("/all/{user_id}")
    public ResponseDto UserPloggings(
            @PathVariable("user_id") Long userId) {
        List<PloggingInfoDto> data = ploggingService.getPloggingListByUser(userId);
        return new ResponseDto(200L, "해당 유저의 플로깅 불러오기 성공", data);
    }

}
