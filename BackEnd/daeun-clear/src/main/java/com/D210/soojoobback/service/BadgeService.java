package com.D210.soojoobback.service;


import com.D210.soojoobback.dto.badge.BadgeListResDto;
import com.D210.soojoobback.entity.Badge;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.entity.UserBadge;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.BadgeRepository;
import com.D210.soojoobback.repository.UserBadgeRepository;
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

    private final BadgeRepository badgeRepository;

    private final UserBadgeRepository userBadgeRepository;

    public List<Badge> getMyBadgeListByUser(User user) {

        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저가 존재하지 않습니다")
        );

        List<Badge> responseDtoList = new ArrayList<>();
        List<UserBadge> userBadges = myUser.getUserBadges();
        for (UserBadge userBadge : userBadges) {

            Badge responseDto = userBadge.getBadge();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional
    public Badge addBadgeToUser(User user, Badge badge) {

        UserBadge userBadge = new UserBadge(user, badge);

        userBadgeRepository.save(userBadge);
        return badge;
    }

    public List<Badge> noHaveBadges(User user) {
        List<Badge> noBadges = badgeRepository.findAll();
        List<UserBadge> badgeIds = userBadgeRepository.findByUserId(user.getId());
        for (UserBadge id : badgeIds) {
            noBadges.remove(id.getBadge());
        }
        return noBadges;
    }
//
    public List<BadgeListResDto> checkAddAll(User user, Long totalCalorie, Long totalDistance, Integer totalTrashCount) {

        List<BadgeListResDto> newBadges = new ArrayList<>();
//        List<Badge> allBadges = badgeRepository.findAll();
        List<Badge> currBadge = new ArrayList<>();
        List<UserBadge> badgeIds = userBadgeRepository.findByUserId(user.getId());
        for (UserBadge id : badgeIds) {
            currBadge.add(id.getBadge());
        }
        System.out.println(currBadge);
        List<Badge> allBadges = badgeRepository.findAll();
        System.out.println(allBadges);

//        List<Badge> possibleBadge = allBadges.removeAll(currBadge);
        for (Badge badge : allBadges) {
            if (!(currBadge.contains(badge))) {
                if (badge.getId() == 1L&&totalTrashCount>=100) {
                    BadgeListResDto b = new BadgeListResDto(badge);
                    UserBadge userBadge = new UserBadge(user, badge);
                    userBadgeRepository.save(userBadge);

                    newBadges.add(b);
                } else if (badge.getId() == 2L&&totalDistance>10) {
                    BadgeListResDto b = new BadgeListResDto(badge);
                    UserBadge userBadge = new UserBadge(user, badge);
                    userBadgeRepository.save(userBadge);
                    newBadges.add(b);
                }
            }
        }

        return newBadges;
    }

    public List<BadgeListResDto> checkAddOne(User user, Double calorie, Double distance, Integer trashCount) {
        List<BadgeListResDto> newBadges = new ArrayList<>();
//        List<Badge> allBadges = badgeRepository.findAll();
        List<Badge> currBadge = new ArrayList<>();
        List<UserBadge> badgeIds = userBadgeRepository.findByUserId(user.getId());
        for (UserBadge id : badgeIds) {
            currBadge.add(id.getBadge());
        }
        System.out.println(currBadge);
        List<Badge> allBadges = badgeRepository.findAll();
        System.out.println(allBadges);

//        List<Badge> possibleBadge = allBadges.removeAll(currBadge);
        for (Badge badge : allBadges) {
            if (!(currBadge.contains(badge))) {
                if (badge.getId() == 3L&&trashCount>=15) {
                    BadgeListResDto b = new BadgeListResDto(badge);
                    UserBadge userBadge = new UserBadge(user, badge);
                    userBadgeRepository.save(userBadge);

                    newBadges.add(b);
                } else if (badge.getId() == 4L&&distance>10) {
                    BadgeListResDto b = new BadgeListResDto(badge);
                    UserBadge userBadge = new UserBadge(user, badge);
                    userBadgeRepository.save(userBadge);
                    newBadges.add(b);
                }
            }
        }
        return newBadges;
    }
}