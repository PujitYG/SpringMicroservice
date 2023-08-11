package com.microservice.authservice.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.authservice.Config.ApplicationUserDetails;
import com.microservice.authservice.DTO.ExceptionDTO;
import com.microservice.authservice.DTO.Token;
import com.microservice.authservice.DTO.UserDetailsDTO;
import com.microservice.authservice.Entity.UserAuthDetails;
import com.microservice.authservice.Exception.UserAlreadyExistException;
import com.microservice.authservice.projections.UserDetailsToken;
import com.microservice.authservice.repository.AuthRepository;
import com.microservice.authservice.services.AuthService;
import com.microservice.authservice.util.JWTUtil;
import com.netflix.discovery.converters.Auto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthRepository repo;
	
	@Autowired
	AuthService authService;
	
	@Value("${jwts.secret}")
	String value;
	
	@Autowired
	JWTUtil jwtutil;
	
	@GetMapping("")
	public String test() {
		return "Hello";
	}
	
	@PostMapping("add/user")
	public ResponseEntity<String> addUser(@Valid @RequestBody UserDetailsDTO userDetails) throws UserAlreadyExistException{
		
		ExceptionDTO dto = new ExceptionDTO();
		dto.setError("Forbidden");
		dto.setMessage("user already exists");
		dto.setTimeStamp(LocalDateTime.now());
		dto.setStatusCode(401);
		
		if(authService.userExists(userDetails)) throw new UserAlreadyExistException(dto);
		
		System.out.println(userDetails.getRoles());
		
		String message = authService.addUser(userDetails);
		
		return ResponseEntity
				.status(200)
				.body(message);
	}
	
	@PostMapping("validate")
	public ResponseEntity<Boolean> validateUser(@RequestBody UserDetailsDTO dto) throws Exception{
		
		boolean message = authService.validateUser(dto);
		
		return ResponseEntity
				.status(200)
				.body(message);
	}
	
	@PostMapping("authenticate")
	public ResponseEntity<Token> authenticateUser(@RequestBody UserDetailsDTO dto) throws Exception {
		
		
		if(!authService.validateUser(dto)) throw new BadCredentialsException("Please enter valid Details");
		
		Token token = authService.generateToken(dto);

		return ResponseEntity.accepted().body(token);
	}
	
	
	@PostMapping("validate/token")
	public Boolean validateToken(@RequestBody Token token) throws Exception {
		return authService.validateToken(token.getJwt());
	}

}
