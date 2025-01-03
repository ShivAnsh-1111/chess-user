package com.online_chess.chess_user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_chess.chess_user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	
	Optional<UserProfile> findByUid(Long id);

}
