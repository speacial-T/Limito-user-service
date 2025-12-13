package com.limito.user_service.model.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponseV1 {

	private UUID addressId;
	private String address;
	private boolean defaultAddress;
}
