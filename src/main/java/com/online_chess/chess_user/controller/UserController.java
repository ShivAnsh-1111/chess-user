package com.online_chess.chess_user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.online_chess.chess_user.constants.ChessUserConstants;
import com.online_chess.chess_user.dto.EmailDto;
import com.online_chess.chess_user.dto.LoginRequest;
import com.online_chess.chess_user.dto.UserDto;
import com.online_chess.chess_user.dto.UserProfileDto;
import com.online_chess.chess_user.entity.User;
import com.online_chess.chess_user.exception.UnauthorizedUser;
import com.online_chess.chess_user.response.ProfileDetails;
import com.online_chess.chess_user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto user, BindingResult result) {
    	log.info("Email :" + user.getEmail()+", isOnline :"+user.isOnline());
    	
    	ResponseEntity<?> response = new ResponseEntity<>(
    			ChessUserConstants.INVALID_REQUEST_BODY,new HttpHeaders(),HttpStatus.BAD_REQUEST);;
    			
    	if(!result.hasErrors()) {
    		try {
        		User newUser = userService.register(user);
        		response = ResponseEntity.ok(newUser);
        	} catch(Exception ex) {
        		response = new ResponseEntity<>(ex.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST);
        	}
    	} 
    	return response;        
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
    	
    	Map<String,String> token;
    	log.info("received request:"+loginRequest);
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
    
    @PostMapping("/profile/save")
    public ResponseEntity<?> saveUserProfile(@RequestBody UserProfileDto profile){
    	
    	ProfileDetails savedProfile = userService.saveUserProfileDetails(profile);
    	
    	return ResponseEntity.ok(savedProfile);
    }
    
    @RequestMapping(path = "/profile/{uid}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProfile(@PathVariable Long uid){
    	
    	ProfileDetails profile = userService.getUserProfileDetails(uid);
    	log.info("User profile found - "+ profile);
    	
    	return ResponseEntity.ok(profile);
    }
    
    @PostMapping("/email/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDto email){
    	
    	EmailDto emailed = userService.saveEmail(email);
    	return ResponseEntity.ok(emailed);

    }
    
    @GetMapping("/email/get/{recipient}")
    public ResponseEntity<?> getEmail(@PathVariable String recipient){

    	List<EmailDto> mails = userService.getEmail(recipient);
    	return ResponseEntity.ok(mails);
    }
    
    @GetMapping("/online")
    public ResponseEntity<?> getOnlineUsers(){
    	List<UserDto> onlineUsers = userService.getOnlineUsers();
    	return ResponseEntity.ok(onlineUsers);
    }
    
    @GetMapping("/logout/{id}")
    public ResponseEntity<?> userLogout(@PathVariable Long id){
    	UserDto user =userService.userLogout(id);
    	return ResponseEntity.ok(user);
    }
}
