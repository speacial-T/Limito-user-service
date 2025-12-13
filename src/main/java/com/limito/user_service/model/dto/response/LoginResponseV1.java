package com.limito.user_service.model.dto.response;

import com.limito.common.audit.UserRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseV1 {

	private Long userId;
	private String email;
	private String name;
	private UserRole role;
	private String brandName;

	private String accessToken;

}
