package com.limito.user_service.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_userAddresses")
public class UserAddress {

	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private UUID addressId;

	@Column(nullable = false, length = 255)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@Builder
	public UserAddress(User user, String address) {
		this.user = user;
		this.address = address;
	}

	public void changeUser(User user) {
		this.user = user;
	}
}
