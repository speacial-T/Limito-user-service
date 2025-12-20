package com.limito.user_service.jwt.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenClaims {
	private final String jwtId;
	private final Long userId;
	private final String loginSessionId;
	private long expiresAt;
}
