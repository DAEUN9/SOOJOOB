package com.D210.soojoobback.repository;

import com.D210.soojoobback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Integer> {


	User findByUsername(String username);

	User findOByEmail(String email);
	Optional<User> findOByUsername(String username);


	Optional<User> findByProviderAndProviderId(String provider, String providerId);


	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);
}


