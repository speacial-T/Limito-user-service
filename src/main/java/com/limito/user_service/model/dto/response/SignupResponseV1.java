package com.limito.user_service.model.dto.response;

import com.limito.common.audit.UserRole;
import com.limito.user_service.model.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseV1 {

	private Long userId;
	private String email;
	private UserRole role;
	private String brandName;
	private String phoneNumber;
	private UserStatus status;

}
