package com.limito.user_service.model.mapper;

import org.springframework.stereotype.Component;

import com.limito.user_service.model.dto.request.UserAddressRequestV1;
import com.limito.user_service.model.dto.response.UserAddressResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserAddress;

@Component
public class UserAddressMapper {

	// 주소 등록을 Address 엔티티로 변환
	public UserAddress toUserAddress(User user, UserAddressRequestV1 request, boolean isDefault) {
		return UserAddress.builder()
			.user(user)
			.address(request.getAddress())
			.defaultAddress(isDefault)
			.build();
	}

	// Address 엔티티를 UserAddressResponse로 변환
	public UserAddressResponseV1 toUserAddressResponse(UserAddress address) {
		return UserAddressResponseV1.builder()
			.addressId(address.getAddressId())
			.address(address.getAddress())
			.defaultAddress(address.isDefaultAddress())
			.build();
	}
}
