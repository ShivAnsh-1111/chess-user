package com.online_chess.chess_user.dto;

import com.online_chess.chess_user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    // getters and setters

    public User toUser(){
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .build();
    }
}