package com.online_chess.chess_user.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserProfileNotFound extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String msg;
	@SuppressWarnings("unused")
	private int code;

}
