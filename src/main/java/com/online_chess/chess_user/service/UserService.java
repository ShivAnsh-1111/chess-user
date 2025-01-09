package com.online_chess.chess_user.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online_chess.chess_user.dto.EmailDto;
import com.online_chess.chess_user.dto.GameDto;
import com.online_chess.chess_user.dto.LoginRequest;
import com.online_chess.chess_user.dto.UserDto;
import com.online_chess.chess_user.dto.UserProfileDto;
import com.online_chess.chess_user.entity.Email;
import com.online_chess.chess_user.entity.Game;
import com.online_chess.chess_user.entity.User;
import com.online_chess.chess_user.entity.UserProfile;
import com.online_chess.chess_user.exception.CustomException;
import com.online_chess.chess_user.exception.UnauthorizedUser;
import com.online_chess.chess_user.exception.UserProfileNotFound;
import com.online_chess.chess_user.repository.EmailRepository;
import com.online_chess.chess_user.repository.GameRepository;
import com.online_chess.chess_user.repository.UserProfileRepository;
import com.online_chess.chess_user.repository.UserRepository;
import com.online_chess.chess_user.response.ProfileDetails;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Service
@Log
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepo;
    
    @Autowired
    private EmailRepository emailRepository;
    
    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public User register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        userDto.setPassword(encodePassword(userDto.getPassword()));
        userDto.setOnline(true);
        log.info("user details:" +userDto.toUser().toString());
        User user =updateLastActivity(userDto);
        UserProfileDto profileDto = UserProfileDto.builder().uid(user.toUserDto().getId()).build();
        log.info("UserProfileDto: "+ profileDto);
        try {
			/*
			 * ObjectMapper mapper = new ObjectMapper(); String profileStr =
			 * mapper.writeValueAsString(profileDto); UserProfile profile =
			 * mapper.readValue(profileStr, UserProfile.class);
			 */
	        
	        userProfileRepo.save(profileDto.toUserProfile());
        } catch(Exception ex) {
        	log.info("ERROR: "+ex.getMessage());;
        }
        return user;
    }

    private String encodePassword(String password) {
        // Add password encoding logic (e.g., BCrypt)
        return password; // Replace with actual encoding
    }
    
    @Transactional
    public Map<String, String> login(LoginRequest loginRequest) {
    	
    	log.info("got data:"+loginRequest);
    	LoginRequest respObj = new LoginRequest("","", false);
    	try {
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String respData = objectMapper.writeValueAsString(loginRequest);
	    	respObj = objectMapper.readValue(respData, LoginRequest.class);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	log.info("got username:"+respObj.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedUser("Username not found",401));

        log.info("Fetched user data:"+ user.toUserDto());
        UserDto userDto = user.toUserDto();
        
        if (!userDto.getPassword().equals(loginRequest.getPassword())) { // Replace with password decoding logic
        	throw new UnauthorizedUser("Invalid password",401) ;
        }

        userDto.setOnline(true);
        updateLastActivity(userDto);
        // Generate a token (e.g., JWT)
        return generateToken(user);
    }

    private Map<String, String> generateToken(User user) {
    	
    	Map<String, String> userDetails = new HashMap<>();
    	
        // Token generation logic (e.g., JWT implementation)
    	userDetails.put("1", "dummy-token"); // Replace with actual token logic
    	
    	userDetails.put("2",String.valueOf(user.toUserDto().getId()));
        return userDetails; 
    }

    @Transactional
    public User getUserById(Long id) {
        // Fetch user by ID
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()){
            throw new RuntimeException("No user found");
        }
        log.info("getUserById -" + user.get().toUserDto().getUsername());
        return user.get();
    }
    
    @Transactional
    public ProfileDetails getUserProfileDetails(Long id) {
    	
    	Optional<User> user = userRepository.findById(id);
    	Optional<UserProfile> userProfile = userProfileRepo.findByUid(id);
    	
    	if(userProfile.isEmpty()) {
    		throw new UserProfileNotFound("No user profile data", 802);
    	}
    	ProfileDetails profile = ProfileDetails.builder()
    								.user(user.get().toUserDto()).profile(userProfile.get().toUserProfileDto()).build();

    	return profile;
    	
    }
    
    @Transactional
    public ProfileDetails saveUserProfileDetails(UserProfileDto profile) {
    	
    	log.info("Received profile:"+profile);
    	Optional<UserProfile> opUserProfile = userProfileRepo.findByUid(profile.getUid());
    	UserProfile userProfile = opUserProfile.get();
    	UserProfileDto profileDto = userProfile.toUserProfileDto();
    	profileDto.setBio(profile.getBio());
    	profileDto.setCity(profile.getCity());
    	profileDto.setCountry(profile.getCountry());
    	profileDto.setPhoneNo(profile.getPhoneNo());
    	profileDto.setState(profile.getState());
    	log.info("Before profile update:"+profileDto);
    	UserProfile savedProfile = userProfileRepo.saveAndFlush(profileDto.toUserProfile());
    	
    	updateLastActivity(userRepository.findById(profile.getUid()).get().toUserDto());
    	
    	return ProfileDetails.builder().profile(savedProfile.toUserProfileDto()).build();
    }
    
    @Transactional
    public EmailDto saveEmail(EmailDto email) {
    	
    	updateLastActivity(userRepository.findByUsername(email.getSender()).get().toUserDto());
    	Email emailed = emailRepository.save(email.toEmail());
    	return emailed.toEmailDto();
    }
    
    @Transactional
    public List<EmailDto> getEmail(String recipient) {
    	Optional<List<Email>> mails = emailRepository.findAllByRecipient(recipient);
    	return mails.get().stream()
    			.map(e -> new EmailDto(e.getId(),e.getRecipient(),e.getSubject(),e.getBody(), e.isReadCheck(),e.getSender()))
    			.collect(Collectors.toList());
    }
    
    @Transactional
    public List<UserDto> getOnlineUsers(){
    	Optional<List<User>> users = userRepository.findAllByIsOnline(true);
    	return users.get().stream()
    			.map(u -> new UserDto(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.isOnline(),u.getLastActivity()))
    			.collect(Collectors.toList());
    }
    
    @Transactional
    public UserDto userLogout(Long id) {
    	
    	UserDto userDto = new UserDto();
    	Optional<User> user = userRepository.findById(id);
    	if(user.isPresent()) {
    		userDto=user.get().toUserDto();
    		userDto.setOnline(false);
    	}
    	
    	return updateLastActivity(userDto).toUserDto();
    }
    
    @Transactional
    public User updateUserActivity(Long id) {
    	Optional<User> user = userRepository.findById(id);
    	if(user.isEmpty()) {
    		throw new UserProfileNotFound("No user found to update Activity !!", 803);
    	}
    	return updateLastActivity(user.get().toUserDto());
    }
    
    @Transactional
    public User getUserByName(String username) {
    	Optional<User> user = userRepository.findByUsername(username);
    	if(user.isEmpty()) {
    		throw new UserProfileNotFound("No user found !!", 804);
    	}
    	return user.get();
    }
    
    @Transactional
    private User updateLastActivity(UserDto user) {
    	user.setLastActivity(LocalDateTime.now());
    	return userRepository.save(user.toUser());
    }
    
    @Transactional
    public GameDto getUserGame(@PathVariable Long id) {
    	Optional<Game> game = gameRepository.findById(id);
    	if(game.isEmpty()) {
    		throw new CustomException("Game not found");
    	}
    	return game.get().toGameDto();
    }
    
    @Transactional
    public GameDto saveUserGame(GameDto game) {
    	return gameRepository.save(game.toGame()).toGameDto();
    }
}
