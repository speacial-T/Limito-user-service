package com.limito.user_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.limito.common.audit.UserRole;
import com.limito.common.exception.AppException;
import com.limito.user_service.jwt.JwtTokenProvider;
import com.limito.user_service.model.dto.request.AdminSignupRequestV1;
import com.limito.user_service.model.dto.request.LoginRequestV1;
import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.response.LoginResponseV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserStatus;
import com.limito.user_service.model.error.UserErrorCode;
import com.limito.user_service.model.mapper.UserMapper;
import com.limito.user_service.model.repository.UserRepositoryV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceV1 {

	private final UserRepositoryV1 userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final JwtTokenProvider jwtTokenProvider;

	@Value("${security.master-key}")
	private String configuredMasterKey;

	@Transactional
	public SignupResponseV1 signUp(SignupRequestV1 request) {

		// ADMIN ROLE 회원가입 제한
		if (request.getRole() == UserRole.ADMIN) {
			throw new AppException(HttpStatus.BAD_REQUEST, "MASTER 계정은 별도 관리자 전용 회원가입을 사용해야 합니다.");
		}

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

		// Mapper로 User 엔티티 생성
		User user = userMapper.toUserEntity(request, encodedPassword, initialStatus);

		// 저장 및 응답 DTO로 변환
		User saved = userRepository.save(user);
		return userMapper.toSignUpResponse(saved);
	}

	private UserStatus decideIntialStatus(UserRole role) {
		if (role == UserRole.COMPANY) {
			return UserStatus.PENDING;
		}
		return UserStatus.APPROVED;
	}

	@Transactional
	public SignupResponseV1 signUpAdmin(AdminSignupRequestV1 request) {

		// 마스터키 검증
		if (!configuredMasterKey.equals(request.getMasterKey())) {
			throw AppException.of(HttpStatus.FORBIDDEN, "마스터키가 올바르지 않습니다.");
		}

		// 이메일 중복 체크
		if (userRepository.existsByEmail(request.getEmail())) {
			throw AppException.of(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
		}

		// 비밀번호 인코딩
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		// Mapper로 ADMIN 유저 엔티티 생성
		User admin = userMapper.toAdminUserEntity(request, encodedPassword);

		// 저장 및 응답 DTO 변환
		User saved = userRepository.save(admin);
		return userMapper.toSignUpResponse(saved);
	}

	@Transactional
	public LoginResponseV1 logIn(LoginRequestV1 request) {

		// 이메일로 유저 조회
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> AppException.of(UserErrorCode.USER_NOT_FOUND));

		// 상태 체크
		if (user.getStatus().equals(UserStatus.PENDING)) {
			throw AppException.of(UserErrorCode.USER_NOT_ACTIVE);
		}

		// 비밀번호 검증
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw AppException.of(UserErrorCode.INVALID_LOGIN);
		}

		// 엑세스 토큰 발급
		String accessToken = jwtTokenProvider.generateAccessToken(
			user.getUserId(),
			user.getEmail(),
			user.getRole()
		);
		long expiresAt = jwtTokenProvider.getAccessTokenExpiresAt();

		// 응답 DTO 변환
		return userMapper.toLoginResponse(user, accessToken);
	}
}
