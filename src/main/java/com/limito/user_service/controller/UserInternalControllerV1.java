package com.limito.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.user_service.model.dto.request.GetOrderedUserInfoRequestV1;
import com.limito.user_service.model.dto.response.GetOrderedUserInfoResponseV1;
import com.limito.user_service.service.OrderedUserServiceV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("internal/api/v1/user")
@RequiredArgsConstructor
public class UserInternalControllerV1 {

	@GetMapping("/{userId}/ordered-user")
	public ResponseEntity<GetOrderedUserInfoResponseV1> getOrderedUserInfo(
		@PathVariable String userId,
		@Valid @RequestBody GetOrderedUserInfoRequestV1 request) {
		GetOrderedUserInfoResponseV1 resopnse = OrderedUserServiceV1.getOrderedUserInfo(userId, request);
		return ResponseEntity.ok(resopnse);
	}
}
