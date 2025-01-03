package com.online_chess.chess_user.dto;

import com.online_chess.chess_user.entity.UserProfile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserProfileDto {
	private Long id;
	private Long uid;
	private String phoneNo;
    private String bio;
	private String city;
	private String state;
	private String country;
	
	public UserProfile toUserProfile() {
		return UserProfile.builder()
				.bio(this.bio)
				.city(this.city)
				.country(this.country)
				.phoneNo(this.phoneNo)
				.state(this.state)
				.uid(this.uid)
				.id(this.id).build();
	}
	@Override
	public String toString() {
		return "UserProfileDto [id=" + id + ", phoneNo=" + phoneNo + ", bio=" + bio + ", city="
				+ city + ", state=" + state + ", country=" + country + ", uid=" + uid +  "]";
	}

}
