package com.limito.user_service.jwt.refreshToken;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {

	private static final String Key_PREFIX = "refreshToken:";
	private final StringRedisTemplate stringRedisTemplate;
	private final ObjectMapper objectMapper;
	private final RedisTemplate<Object, Object> redisTemplate;

	// Redis 저장
	public void save(String jwtId, RefreshTokenRecord refreshTokenRecord, Duration ttl) {
		if (ttl == null || ttl.isZero() || ttl.isNegative()) {
			throw new IllegalArgumentException("ttl is null or zero or negative");
		}
		String key = key(jwtId);
		redisTemplate.opsForValue().set(key, toJson(refreshTokenRecord), ttl);
	}

	// Redis 조회
	public RefreshTokenRecord find(String jwtId) {
		String key = key(jwtId);
		String json = stringRedisTemplate.opsForValue().get(key);
		if (json == null) {
			return null;
		}
		return fromJson(json);
	}

	// Redis 폐기
	public void revoke(String jwtId) {
		RefreshTokenRecord refreshTokenRecord = find(jwtId);
		if (refreshTokenRecord == null) {
			return;
		}

		RefreshTokenRecord revoked = RefreshTokenRecord.builder()
			.userId(refreshTokenRecord.getUserId())
			.loginSessionId(refreshTokenRecord.getLoginSessionId())
			.refreshHash(refreshTokenRecord.getRefreshHash())
			.refreshTokenStatus(RefreshTokenStatus.REVOKED)
			.expiresAt(refreshTokenRecord.getExpiresAt())
			.build();

		Duration ttl = remainingTtlFromExpiresAt(refreshTokenRecord.getExpiresAt());
		if (ttl.isZero() || ttl.isNegative()) {
			delete(jwtId);
			return;
		}
		save(jwtId, revoked, ttl);
	}

	// Redis 삭제
	public void delete(String jwtId) {
		stringRedisTemplate.delete(key(jwtId));
	}

	private String key(String jwtId) {
		if (jwtId == null || jwtId.isBlank()) {
			throw new IllegalArgumentException("jwtId is blank");
		}
		return Key_PREFIX + jwtId;
	}

	private String toJson(RefreshTokenRecord refreshTokenRecord) {
		try {
			return objectMapper.writeValueAsString(refreshTokenRecord);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("failed to serialize RefreshTokenRecord");
		}
	}

	private RefreshTokenRecord fromJson(String json) {
		try {
			return objectMapper.readValue(json, RefreshTokenRecord.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("failed to deserialize RefreshTokenRecord");
		}
	}

	private Duration remainingTtlFromExpiresAt(Long expiresAt) {
		long now = System.currentTimeMillis();
		long remainMillis = expiresAt - now;
		if (remainMillis <= 0) {
			return Duration.ZERO;
		}
		return Duration.ofMillis(remainMillis);
	}
}
