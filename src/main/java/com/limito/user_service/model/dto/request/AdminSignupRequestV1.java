package com.limito.user_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
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

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String masterKey;

}
