package com.online_chess.chess_user.dto;

import java.time.LocalDateTime;

import com.online_chess.chess_user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isOnline;
    private LocalDateTime lastActivity;
    
    // getters and setters

    public User toUser(){
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .isOnline(this.isOnline)
                .lastActivity(this.lastActivity)
                .build();
    }

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", isOnline=" + isOnline +  ", lastActivity=" + lastActivity +"]";
	}
}