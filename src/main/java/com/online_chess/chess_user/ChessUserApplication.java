package com.online_chess.chess_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.online_chess.chess_user.entity"})
public class ChessUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessUserApplication.class, args);
	}

}
