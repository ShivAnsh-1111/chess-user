package com.online_chess.chess_user.response;

import com.online_chess.chess_user.dto.UserDto;
import com.online_chess.chess_user.dto.UserProfileDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProfileDetails {
	
	UserDto user;
	UserProfileDto profile;
	
	@Override
	public String toString() {
		return "ProfileDetails [user=" + user + ", profile=" + profile + "]";
	}

	
}
