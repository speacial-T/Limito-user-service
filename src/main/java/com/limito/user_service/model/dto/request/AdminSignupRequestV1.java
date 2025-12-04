package com.limito.user_service.model.dto.request;

import com.limito.common.entity.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSignupRequestV1 {

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotNull
	private UserRole role;

	private String brandName;

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String masterKey;

}
