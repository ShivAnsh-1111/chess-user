package com.online_chess.chess_user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
    private boolean isOnline;
    // getters and setters
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Username: "+this.username+", Password:"+this.password+", isOnline:"+this.isOnline;
    }
}
