package com.online_chess.chess_user.dto;

import com.online_chess.chess_user.entity.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

	private Long id;
    private String recipient;
    private String subject;
    private String body;
    private boolean readCheck;
    private String sender;
    
    
	@Override
	public String toString() {
		return "EmailDTO [recipient=" + recipient + ", id=" + id +", subject=" + subject + ", body=" + body + ", readCheck=" + readCheck + ", sender=" + sender + "]";
	}
    
	public Email toEmail() {
		return Email.builder()
				.id(this.id)
				.body(this.body)
				.recipient(this.recipient)
				.subject(this.subject)
				.readCheck(this.readCheck)
				.sender(this.sender).build();
	}
    
    
}
