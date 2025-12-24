package com.limito.user_service.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.limito.common.audit.UserRole;
import com.limito.common.exception.AppException;
import com.limito.user_service.jwt.refreshToken.RefreshTokenClaims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.access-token-expire-minutes}")
	private long accessTokenExpireMinutes;

	@Value("${security.jwt.refresh-token-expire-days}")
	private long refreshTokenExpireDays;

	// AccessToken 생성
	public String generateAccessToken(Long userId, UserRole role) {
		Instant now = Instant.now();
		Instant expiry = now.plus(accessTokenExpireMinutes, ChronoUnit.MINUTES);

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("X-User-Role", role.name())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(expiry))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	// RefreshToken 생성
	public String generateRefreshToken(Long userId, String loginSessionId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(refreshTokenExpireDays, ChronoUnit.DAYS);
		String jwtId = UUID.randomUUID().toString();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.setId(jwtId)
			.claim("loginSessionId", loginSessionId)
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(expiry))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	// RefreshToken 파싱
	public RefreshTokenClaims parseRefreshToken(String refreshToken) {
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(refreshToken)
				.getBody();

			String jwtId = claims.getId();
			String loginSessionId = claims.get("loginSessionId", String.class);

			if (jwtId == null || loginSessionId == null) {
				throw AppException.of(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN");
			}

			Long userId = Long.valueOf(claims.getSubject());
			long expiresAt = claims.getExpiration().getTime();

			return new RefreshTokenClaims(jwtId, userId, loginSessionId, expiresAt);
		} catch (ExpiredJwtException e) {
			throw AppException.of(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED");
		} catch (JwtException | IllegalArgumentException e) {
			throw AppException.of(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN");
		}
	}
}
