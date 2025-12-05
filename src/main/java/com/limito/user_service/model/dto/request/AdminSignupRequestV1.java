package com.limito.user_service.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSignupRequestV1 {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	@Pattern(
		regexp = "^010-\\d{4}-\\d{4}$",
		message = "전화번호 형식은 010-1234-5678 형태여야 합니다."
	)
	private String phoneNumber;

	@NotBlank
	private String masterKey;

}
