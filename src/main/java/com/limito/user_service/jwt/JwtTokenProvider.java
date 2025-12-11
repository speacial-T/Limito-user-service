package com.limito.user_service.jwt;

import java.time.Instant;
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

	public String generateAccessToken(Long userId, UserRole role) {
		Instant now = Instant.now();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("X-User-Role", role.name())
			.setIssuedAt(Date.from(now))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

}
