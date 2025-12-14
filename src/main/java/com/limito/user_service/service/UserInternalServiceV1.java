package com.limito.user_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.limito.common.exception.AppException;
import com.limito.user_service.model.dto.response.OrderedUserInfoResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserAddress;
import com.limito.user_service.model.error.UserErrorCode;
import com.limito.user_service.model.mapper.OrderedUserInfoMapper;
import com.limito.user_service.model.repository.UserAddressRepositoryV1;
import com.limito.user_service.model.repository.UserRepositoryV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInternalServiceV1 {

	private final UserRepositoryV1 userRepository;
	private final UserAddressRepositoryV1 userAddressRepository;
	private final OrderedUserInfoMapper orderedUserInfoMapper;

	@Transactional(readOnly = true)
	public OrderedUserInfoResponseV1 getOrderedUserInfo(Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> AppException.of(UserErrorCode.USER_NOT_FOUND));
		UserAddress userAddress = userAddressRepository.findByUserUserIdAndDefaultAddressTrue(userId)
			.orElseThrow(() -> AppException.of(UserErrorCode.DEFAULT_ADDRESS_NOT_FOUND));

		return orderedUserInfoMapper.toOrderedUserInfoResponse(user, userAddress);
	}
}
