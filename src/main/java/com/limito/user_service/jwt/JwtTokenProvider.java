package com.limito.user_service.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.limito.common.audit.UserRole;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	// key객체 생성
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(Long userId, UserRole role) {

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("X-User-Role", role.name())
			.setIssuedAt(new Date())
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

}
