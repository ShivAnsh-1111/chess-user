package com.online_chess.chess_user.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class ChessKafkaReceiver {
	
	/*
	 * @Autowired
	 * 
	 * @Qualifier("listenerContainerFactory") KafkaListenerContainerFactory
	 * listenerContainerFactory;
	 */
	
	@KafkaListener(topics = "chess.match" /* , containerFactory = listenerContainerFactory */)
	public void receive(String msg) {
		log.info(msg);
	}

}
