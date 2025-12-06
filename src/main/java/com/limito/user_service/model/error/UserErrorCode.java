package com.limito.user_service.model.error;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
	USER_NOT_ACTIVE(HttpStatus.FORBIDDEN, "승인 처리되지 않은 사용자입니다."),
	INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");

	private final HttpStatus status;
	private final String message;

}
