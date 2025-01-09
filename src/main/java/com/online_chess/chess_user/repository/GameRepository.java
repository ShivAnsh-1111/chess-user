package com.online_chess.chess_user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online_chess.chess_user.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findTopByOrderByIdDesc();

	Game getGameById(Long id);
}
