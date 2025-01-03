package com.online_chess.chess_user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online_chess.chess_user.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long>{
	
	Optional<List<Email>> findAllByRecipient(String recipient);

}
