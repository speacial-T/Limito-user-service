package com.limito.user_service.model.dto.response;

import java.time.LocalDateTime;

import com.limito.user_service.model.entity.UserStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PendingCompanyResponseV1 {

	private Long userId;
	private String email;
	private String name;
	private String brandName;
	private String phoneNumber;
	private UserStatus status;
	private LocalDateTime createdAt;

}
