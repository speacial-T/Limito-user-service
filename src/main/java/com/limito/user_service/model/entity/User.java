package com.limito.user_service.model.entity;

import com.limito.common.entity.BaseEntity;
import com.limito.common.entity.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long userId;

	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(length = 100)
	private String brandName;

	@Column(nullable = false, length = 20)
	private String phoneNumber;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Builder
	public User(UserRole role,
		String email,
		String password,
		String brandName,
		String phoneNumber,
		UserStatus status) {
		this.role = role;
		this.email = email;
		this.password = password;
		this.brandName = brandName;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}
}
