package com.limito.user_service.model.mapper;

import org.springframework.stereotype.Component;

import com.limito.user_service.model.dto.response.OrderedUserInfoResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserAddress;

@Component
public class OrderedUserInfoMapper {

	public OrderedUserInfoResponseV1 toOrderedUserInfoResponse(User user, UserAddress userAddress) {
		return OrderedUserInfoResponseV1.builder()
			.userId(user.getUserId())
			.receiverName(user.getName())
			.phoneNumber(user.getPhoneNumber())
			.deliveryAddress(deliveryAddress(userAddress))
			.build();
	}

	private String deliveryAddress(UserAddress userAddress) {
		return userAddress.getAddress();
	}
}
