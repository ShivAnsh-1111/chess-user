package com.online_chess.chess_user.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChessKafkaReceiver {

    private static final Logger log = LoggerFactory.getLogger(ChessKafkaReceiver.class);
	
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
