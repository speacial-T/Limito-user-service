package com.limito.user_service.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.user_service.model.entity.UserAddress;

public interface UserAddressRepositoryV1 extends JpaRepository<UserAddress, Long> {

	List<UserAddress> findByUserUserId(Long userId);
}
