package com.limito.user_service.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.limito.common.audit.UserRole;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.secret-key}")
	private String secretKey;
	@Value("${security.jwt.access-token-expire-minutes:30}")
	private long accessTokenExpireMinutes;

	public String generateAccessToken(Long userId, String email, UserRole role) {
		Instant now = Instant.now();
		Instant expiry = now.plus(accessTokenExpireMinutes, ChronoUnit.MINUTES);

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("X-User-Email", email)
			.claim("X-User-Role", role.name())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(expiry))
			.signWith(SignatureAlgorithm.ES256, secretKey)
			.compact();
	}

	public long getAccessTokenExpiresAt() {
		return Instant.now()
			.plus(accessTokenExpireMinutes, ChronoUnit.MINUTES)
			.toEpochMilli();
	}
}
