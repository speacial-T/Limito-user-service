package com.limito.user_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.user_service.model.dto.request.AdminSignupRequestV1;
import com.limito.user_service.model.dto.request.CompanyApprovalRequestV1;
import com.limito.user_service.model.dto.request.LoginRequestV1;
import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.request.UserAddressRequestV1;
import com.limito.user_service.model.dto.response.LoginResponseV1;
import com.limito.user_service.model.dto.response.PendingCompanyResponseV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.model.dto.response.UserAddressResponseV1;
import com.limito.user_service.model.repository.UserRepositoryV1;
import com.limito.user_service.service.UserServiceV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserControllerV1 {

	private final UserServiceV1 userService;
	private final UserRepositoryV1 userRepositoryV1;

	// USER, COMPANY 회원가입
	@PostMapping("/auth/signup")
	public ResponseEntity<SignupResponseV1> signUp(@Valid @RequestBody SignupRequestV1 request) {
		SignupResponseV1 response = userService.signUp(request);
		return ResponseEntity.ok(response);
	}

	// MASTER 회원가입
	@PostMapping("/auth/signup/admin")
	public ResponseEntity<SignupResponseV1> signUpAdmin(@Valid @RequestBody AdminSignupRequestV1 request) {
		SignupResponseV1 response = userService.signUpAdmin(request);
		return ResponseEntity.ok(response);
	}

	// 로그인
	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponseV1> logIn(@Valid @RequestBody LoginRequestV1 request) {
		LoginResponseV1 response = userService.logIn(request);
		return ResponseEntity.ok(response);
	}

	// PENDING 회사 목록 조회
	@GetMapping("/auth/requests")
	public ResponseEntity<Page<PendingCompanyResponseV1>> getPendingCompany(Pageable pageable) {
		Page<PendingCompanyResponseV1> page = userService.getPendingCompany(pageable);
		return ResponseEntity.ok(page);
	}

	// 승인 또는 거절 처리
	@PostMapping("/auth/requests/{userId}")
	public ResponseEntity<PendingCompanyResponseV1> updateCompanyStatus(
		@PathVariable Long userId,
		@Valid @RequestBody CompanyApprovalRequestV1 request,
		@RequestHeader("X-User-Id") Long adminId,
		@RequestHeader("X-User-Role") String role) {
		PendingCompanyResponseV1 response = userService.updateCompanyStatus(userId, request, adminId, role);
		return ResponseEntity.ok(response);
	}

	// 주소 생성
	@PostMapping("/user/me")
	public ResponseEntity<UserAddressResponseV1> createAddress(
		@RequestHeader("X-User-Id") Long userId,
		@Valid @RequestBody UserAddressRequestV1 request) {
		UserAddressResponseV1 response = userService.createAddress(userId, request);
		return ResponseEntity.ok(response);
	}

	// 주소 조회
	@GetMapping("/user/me")
	public ResponseEntity<List<UserAddressResponseV1>> getAddresses(@RequestHeader("X-User-Id") Long userId) {
		List<UserAddressResponseV1> list = userService.getAddresses(userId);
		return ResponseEntity.ok(list);
	}

}
