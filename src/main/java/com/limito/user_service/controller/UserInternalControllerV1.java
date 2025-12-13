package com.limito.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.user_service.model.dto.response.OrderedUserInfoResponseV1;
import com.limito.user_service.service.UserInternalServiceV1;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("internal/api/v1/user")
@RequiredArgsConstructor
public class UserInternalControllerV1 {

	private final UserInternalServiceV1 userInternalService;

	@GetMapping("/{userId}/ordered-user")
	public ResponseEntity<OrderedUserInfoResponseV1> getOrderedUserInfo(@PathVariable Long userId) {
		OrderedUserInfoResponseV1 resopnse = userInternalService.getOrderedUserInfo(userId);
		return ResponseEntity.ok(resopnse);
	}
}
