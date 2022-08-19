package com.D210.soojoobback.repository;

import com.D210.soojoobback.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {


    List<UserBadge> findByUserId(Long userId);

    List<UserBadge> findByBadgeId(Long badgeId);



}