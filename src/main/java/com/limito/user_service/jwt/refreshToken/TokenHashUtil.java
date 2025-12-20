package com.limito.user_service.jwt.refreshToken;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenHashUtil {
	private TokenHashUtil() {
	}

	public static String sha256(String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new IllegalArgumentException("refreshToken is null or empty");
		}
		try {
			// SHA-256 해시 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] encoded = md.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
			return toHex(encoded);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 algorithm not found", e);
		}
	}

	private static String toHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
