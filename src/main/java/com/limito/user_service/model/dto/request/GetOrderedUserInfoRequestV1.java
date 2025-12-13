package com.limito.user_service.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetOrderedUserInfoRequestV1 {

	@NotNull
	private String name;

	@NotNull
	private String phoneNumber;

	@NotNull
	private String address;

}
