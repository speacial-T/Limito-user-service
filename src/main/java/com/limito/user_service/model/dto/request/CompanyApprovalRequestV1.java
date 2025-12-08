package com.limito.user_service.model.dto.request;

import com.limito.user_service.model.entity.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyApprovalRequestV1 {

	@NotNull
	private UserStatus status;

}
