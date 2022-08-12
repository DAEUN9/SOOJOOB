package com.D210.soojoobback.controller;




import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.Badge;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.BadgeRepository;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.security.UserDetailsImpl;
import com.D210.soojoobback.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("badges")
public class BadgeController {


    private final BadgeService badgeService;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;


    private void checkLogin(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails);
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.2");
        }
    }
    @GetMapping("")
    public ResponseDto myBadges(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        User user = userDetails.getUser();
        List<Badge> data = badgeService.getMyBadgeListByUser(user);
        System.out.println(data);
        return new ResponseDto(200L,"내 뱃지 불러오기", data);
    }

//
    @PostMapping("/{user_id}/{badge_id}")
    public ResponseDto addBadges(@PathVariable("user_id") long user_id, @PathVariable("badge_id") long badge_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new CustomErrorException("유저가 존재하지 않습니다")
        );
        Badge badge = badgeRepository.findById(badge_id).orElseThrow(
                () -> new CustomErrorException("뱃지가 존재하지 않습니다")
        );

        Badge data = badgeService.addBadgeToUser(user, badge);

        System.out.println(data);
        System.out.println("---------------------------------");
//        System.out.println(user.getBadges());
        return new ResponseDto(200L,"추가된 뱃지", data);
    }

    @GetMapping("no")
    public ResponseDto addBadges(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        User user = userDetails.getUser();
        List<Badge> data = badgeService.noHaveBadges(user);

        System.out.println(data);
        System.out.println("---------------------------------");
//        System.out.println(user.getBadges());
        return new ResponseDto(200L,"미보유한 뱃지", data);
    }
}
