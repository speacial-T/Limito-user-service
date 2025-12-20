package com.limito.user_service.jwt.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRecord {
	private Long userId;
	private String loginSessionId;
	private String refreshHash;
	private RefreshTokenStatus refreshTokenStatus;
	private long expiresAt;
}
