package com.limito.user_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.user_service.model.dto.request.AdminSignupRequestV1;
import com.limito.user_service.model.dto.request.CompanyApprovalRequestV1;
import com.limito.user_service.model.dto.request.LoginRequestV1;
import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.response.LoginResponseV1;
import com.limito.user_service.model.dto.response.PendingCompanyResponseV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.service.UserServiceV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserControllerV1 {

	private final UserServiceV1 userService;

	// USER, COMPANY 회원가입
	@PostMapping("/signup")
	public ResponseEntity<SignupResponseV1> signUp(@Valid @RequestBody SignupRequestV1 request) {
		SignupResponseV1 response = userService.signUp(request);
		return ResponseEntity.ok(response);
	}

	// MASTER 회원가입
	@PostMapping("/signup/admin")
	public ResponseEntity<SignupResponseV1> signUpAdmin(@Valid @RequestBody AdminSignupRequestV1 request) {
		SignupResponseV1 response = userService.signUpAdmin(request);
		return ResponseEntity.ok(response);
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<LoginResponseV1> logIn(@Valid @RequestBody LoginRequestV1 request) {
		LoginResponseV1 response = userService.logIn(request);
		return ResponseEntity.ok(response);
	}

	// PENDING 회사 목록 조회
	@GetMapping("/signup-requests")
	public ResponseEntity<Page<PendingCompanyResponseV1>> getPendingCompany(Pageable pageable) {
		Page<PendingCompanyResponseV1> response = userService.getPendingCompany(pageable);
		return ResponseEntity.ok(response);
	}

	// 승인 또는 거절 처리
	@PostMapping("/signup-requests/{userId}")
	public ResponseEntity<PendingCompanyResponseV1> updateCompanyStatus(
		@PathVariable Long userId,
		@Valid @RequestBody CompanyApprovalRequestV1 request) {
		PendingCompanyResponseV1 response = userService.updateCompanyStatus(userId, request);
		return ResponseEntity.ok(response);
	}

}
