package com.limito.user_service.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrderedUserInfoResponseV1 {

	private String name;
	private String phoneNumber;
	private String address;

}
