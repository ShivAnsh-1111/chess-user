package com.online_chess.chess_user.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

public class KafkaConsumerConfig {
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "game");
		return new DefaultKafkaConsumerFactory<>(configProps);
		
	}
	
	@Bean("listenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = 
		        new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
