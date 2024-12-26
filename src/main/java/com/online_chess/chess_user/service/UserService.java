package com.online_chess.chess_user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online_chess.chess_user.dto.LoginRequest;
import com.online_chess.chess_user.dto.UserDto;
import com.online_chess.chess_user.entity.User;
import com.online_chess.chess_user.exception.UnauthorizedUser;
import com.online_chess.chess_user.repository.UserRepository;

import lombok.extern.java.Log;

@Service
@Log
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        userDto.setPassword(encodePassword(userDto.getPassword()));
        log.info("user details:" + userDto.getUsername()+ userDto.getEmail()+ userDto.getPassword() );
        return userRepository.save(userDto.toUser());
    }

    private String encodePassword(String password) {
        // Add password encoding logic (e.g., BCrypt)
        return password; // Replace with actual encoding
    }
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedUser("Username not found",401));

        UserDto userDto = user.toUserDto();
        if (!userDto.getPassword().equals(loginRequest.getPassword())) { // Replace with password decoding logic
        	throw new UnauthorizedUser("Invalid password",401) ;
        }

        // Generate a token (e.g., JWT)
        return generateToken(user);
    }

    private String generateToken(User user) {
        // Token generation logic (e.g., JWT implementation)
        return "dummy-token"; // Replace with actual token logic
    }

    public User getUserById(Long id) {
        // Fetch user by ID
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()){
            throw new RuntimeException("No user found");
        }
        log.info("getUserById -" + user.get().toUserDto().getUsername());
        return user.get();
    }
}
