package com.limito.user_service.model.mapper;

import org.springframework.stereotype.Component;

import com.limito.common.audit.UserRole;
import com.limito.user_service.model.dto.request.AdminSignupRequestV1;
import com.limito.user_service.model.dto.request.SignupRequestV1;
import com.limito.user_service.model.dto.response.LoginResponseV1;
import com.limito.user_service.model.dto.response.SignupResponseV1;
import com.limito.user_service.model.entity.User;
import com.limito.user_service.model.entity.UserStatus;

@Component
public class UserMapper {

	// 일반 회원가입을 User 엔티티로 변환
	public User toUserEntity(SignupRequestV1 req, String encodedPassword, UserStatus initialStatus) {
		return User.builder()
			.role(req.getRole())
			.email(req.getEmail())
			.password(encodedPassword)
			.brandName(req.getBrandName())
			.phoneNumber(req.getPhoneNumber())
			.status(initialStatus)
			.build();
	}

	// ADMIN 회원가입을 User 엔티티로 변환
	public User toAdminUserEntity(AdminSignupRequestV1 req, String encodedPassword) {
		return User.builder()
			.role(UserRole.ADMIN)
			.email(req.getEmail())
			.password(encodedPassword)
			.phoneNumber(req.getPhoneNumber())
			.status(UserStatus.APPROVED)
			.build();
	}

	// User 엔티티를 SignUpResponse로 변환
	public SignupResponseV1 toSignUpResponse(User user) {
		return SignupResponseV1.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.role(user.getRole())
			.brandName(user.getBrandName())
			.phoneNumber(user.getPhoneNumber())
			.status(user.getStatus())
			.build();
	}

	// User 엔티티를 LogInResponse로 변환
	public LoginResponseV1 toLoginResponse(User user, String accessToken) {
		return LoginResponseV1.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.role(user.getRole())
			.brandName(user.getBrandName())
			.accessToken(accessToken)
			.build();
	}
}
