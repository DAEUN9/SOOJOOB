package com.D210.soojoobback.controller;




import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.Badge;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.BadgeRepository;
import com.D210.soojoobback.repository.UserRepository;
import com.D210.soojoobback.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BadgeController {


    private final BadgeService badgeService;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;


    @GetMapping("/badges/{user_id}")
    public ResponseDto myBadges(@PathVariable("user_id") long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new CustomErrorException("유저가 존재하지 않습니다")
        );
        List<Badge> data = badgeService.getMyBadgeListByUser(user);
        System.out.println(data);
        return new ResponseDto(200L,"내 뱃지 불러오기", data);
    }

//
    @PostMapping("/badges/{user_id}/{badge_id}")
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
}
