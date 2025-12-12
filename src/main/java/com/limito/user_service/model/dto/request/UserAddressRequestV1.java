package com.limito.user_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddressRequestV1 {

	@NotBlank
	private String address;

	private Boolean defaultAddress;
}
