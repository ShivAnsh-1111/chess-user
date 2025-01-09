package com.online_chess.chess_user.entity;

import com.online_chess.chess_user.dto.UserProfileDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private Long uid;
	private String phoneNo;
    private String bio;
	private String city;
	private String state;
	private String country;
	
	public UserProfileDto toUserProfileDto() {
		return UserProfileDto.builder()
				.uid(this.uid)
				.bio(this.bio)
				.city(this.city)
				.country(this.country)
				.phoneNo(this.phoneNo)
				.state(this.state)
				.id(this.id).build();
	}
	
	@Override
	public String toString() {
		return "UserProfile [aid=" + id + ", phoneNo=" + phoneNo + ", bio=" + bio + ", city="
				+ city + ", state=" + state + ", country=" + country + ", uid=" + uid + "]";
	}
	
	
}
