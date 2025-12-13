package com.limito.user_service.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderedUserInfoResponseV1 {

	private Long userId;
	private String name;
	private String phoneNumber;
	private String address;

}
