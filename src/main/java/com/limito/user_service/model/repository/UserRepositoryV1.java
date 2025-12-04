package com.limito.user_service.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.common.entity.UserRole;
import com.limito.user_service.model.entity.User;

public interface UserRepositoryV1 extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByRole(UserRole role);
}
