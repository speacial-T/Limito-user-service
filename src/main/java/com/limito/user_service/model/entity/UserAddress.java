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

	@Column(nullable = false)
	private boolean defaultAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@Builder
	public UserAddress(User user, String address, boolean defaultAddress) {
		this.user = user;
		this.address = address;
		this.defaultAddress = defaultAddress;
	}

	public void changeUser(User user) {
		this.user = user;
	}

	public void setDefault(boolean value) {
		this.defaultAddress = value;
	}
}
