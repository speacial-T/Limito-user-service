package com.limito.user_service.model.dto.response;

import com.limito.common.entity.UserRole;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseV1 {

	private Long userId;
	private String email;
	private UserRole role;
	private String brandName;
	private String phoneNumber;
	private UserStatus status;

	public static SignupResponseV1 from(User user) {
		return new SignupResponseV1(
			user.getUserId(),
			user.getEmail(),
			user.getRole(),
			user.getBrandName(),
			user.getPhoneNumber(),
			user.getStatus()
		);
	}

}
