package com.online_chess.chess_user.entity;

import com.online_chess.chess_user.dto.EmailDto;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
		private Long id;
		@Nonnull
		private String recipient;
		private String subject;
		private String body;
		private boolean readCheck;
		@Nonnull
		private String sender;
		 
		@Override
		public String toString() {
			return "Email [recipient=" + recipient + ", subject=" + subject + ", body=" + body + ", readCheck=" + readCheck + ", sender=" + sender + "]";
		}
		 
		public EmailDto toEmailDto() {
			return EmailDto.builder()
					.id(this.id)
					.body(this.body)
					.recipient(this.recipient)
					.subject(this.subject)
					.readCheck(this.readCheck)
					.sender(this.sender).build();
		}

}
