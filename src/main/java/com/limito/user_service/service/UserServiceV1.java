package com.limito.user_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.limito.common.entity.UserRole;
import com.limito.common.exception.AppException;
import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserStatus;
import com.limito.user_service.model.repository.UserRepositoryV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceV1 {

	private final UserRepositoryV1 userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public SignupResponseV1 signUp(SignupRequestV1 request) {

		// 이메일 중복 체크
		if (userRepository.existsByEmail(request.getEmail())) {
			throw AppException.of(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
		}

		// company이면 brandName 체크
		if (request.getRole() == UserRole.COMPANY && !StringUtils.hasText(request.getBrandName())) {
			throw AppException.of(HttpStatus.BAD_REQUEST, "Company 회원은 브랜드 이름이 필수입니다.");
		}

		// 비밀번호 인코딩
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		// 유저 초기 상태 처리
		UserStatus initialStatus = decideIntialStatus(request.getRole());

		// 유저 엔티티 생성
		User user = User.builder()
			.email(request.getEmail())
			.password(encodedPassword)
			.role(request.getRole())
			.brandName(request.getBrandName())
			.phoneNumber(request.getPhoneNumber())
			.status(initialStatus)
			.build();

		// TODO: 주소가 있으면 UserAddress 추가(cascade)

		// 저장 및 응답 DTO로 변환
		User savedUser = userRepository.save(user);
		return SignupResponseV1.from(savedUser);
	}

	private UserStatus decideIntialStatus(UserRole role) {
		if (role == UserRole.COMPANY) {
			return UserStatus.PENDING;
		}
		return UserStatus.APPROVED;
	}

}
