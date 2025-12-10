package com.limito.user_service.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserStatus;

public interface UserRepositoryV1 extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Page<User> findByStatus(UserStatus status, Pageable pageable);

	Optional<User> findByUserIdAndStatus(Long userId, UserStatus status);
}
