package com.limito.user_service.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseV1 {
	private String accessToken;

	public static TokenResponseV1 of(String accessToken) {
		return TokenResponseV1.builder()
			.accessToken(accessToken)
			.build();
	}
}
