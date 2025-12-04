package com.limito.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.service.UserServiceV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserControllerV1 {

	private final UserServiceV1 userService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponseV1> signUp(@Valid @RequestBody SignupRequestV1 request) {
		SignupResponseV1 response = userService.signUp(request);
		return ResponseEntity.ok(response);
	}
}
