package com.finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EntityResponse {
	
	public static <T> ResponseEntity<T> success(T body){
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	public static ResponseEntity<String> failure(String message){
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
