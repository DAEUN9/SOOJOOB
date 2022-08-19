package com.D210.soojoobback.repository;

import com.D210.soojoobback.entity.Plogging;
import com.D210.soojoobback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PloggingRepository extends JpaRepository<Plogging, Integer> {

    Optional<Plogging> findByPloggingUser(User ploggingUser);


    Optional<Plogging> findById(Long id);
}
