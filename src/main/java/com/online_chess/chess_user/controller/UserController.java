package com.online_chess.chess_user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online_chess.chess_user.dto.LoginRequest;
import com.online_chess.chess_user.dto.UserDto;
import com.online_chess.chess_user.entity.User;
import com.online_chess.chess_user.exception.UnauthorizedUser;
import com.online_chess.chess_user.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto user) {
    	log.info("Username :" + user.getUsername());
    	User newUser;
    	try {
        newUser = userService.register(user);
    	} catch(Exception ex) {
    		return new ResponseEntity<>(ex.getMessage(),new HttpHeaders(),HttpStatus.CONFLICT); 
    	}
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
    	
    	String token;
    	try {
        token = userService.login(loginRequest);
    	} catch (UnauthorizedUser ex) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMsg());
    	}
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        log.info("in user controller - "+ user.toUserDto().getEmail());
        return ResponseEntity.ok(user.toUserDto());
    }
}
