package com.limito.user_service.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.limito.user_service.model.entity.UserAddress;

public interface UserAddressRepositoryV1 extends JpaRepository<UserAddress, UUID> {

	Optional<UserAddress> findByUserUserIdAndDefaultAddressTrue(Long userId);

	List<UserAddress> findByUserUserId(Long userId);

	long countByUserUserId(Long userUserId);

	// 유저의 기본배송지를 초기화
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(
		"update UserAddress a " +
			"set a.defaultAddress = false " +
			"where a.user.userId = :userId and a.defaultAddress = true")
	void clearDefaultAddress(@Param("userId") Long userId);
}
