package com.microservice.authservice.Exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.authservice.DTO.ExceptionDTO;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	
	@ExceptionHandler(value = UserAlreadyExistException.class)
	public ResponseEntity<ExceptionDTO> userAlreadyExist(UserAlreadyExistException exe){
		return exe.getResponseEntity();
	}
	

//	@ExceptionHandler(value = BadCredentialsException.class)
//	public ResponseEntity<ExceptionDTO> exception(BadCredentialsException exe){
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setError("forbidden");
//		dto.setMessage(exe.getMessage());
//		dto.setTimeStamp(LocalDateTime.now());
//		dto.setStatusCode(500);
//		return ResponseEntity
//				.status(500)
//				.body(dto);
//	}
	
	
}
