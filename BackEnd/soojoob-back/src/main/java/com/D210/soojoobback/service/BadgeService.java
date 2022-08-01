package com.D210.soojoobback.service;


import com.D210.soojoobback.dto.badge.BadgeListResDto;
import com.D210.soojoobback.entity.Badge;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final UserRepository userRepository;

    public List<BadgeListResDto> getMyBadgeListByUser(User user) {

        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저가 존재하지 않습니다")
        );

        List<BadgeListResDto> responseDtoList = new ArrayList<>();
        List<Badge> badgeList = myUser.getBadges();
        for (Badge badge : badgeList) {
            BadgeListResDto responseDto = badge.toBuildBadge();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional
    public Badge addBadgeToUser(User user, Badge badge) {
        user.addBadge(badge);
        return badge;
    }


}