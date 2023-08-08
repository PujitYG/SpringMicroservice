package com.microservice.authservice.Config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.microservice.authservice.DTO.ExceptionDTO;
import com.microservice.authservice.Entity.UserAuthDetails;
import com.microservice.authservice.repository.AuthRepository;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

	@Autowired
	private AuthRepository authRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserAuthDetails> user = authRepository.findByUsernameIgnoreCase(username);
		
		if(!user.isPresent()) throw new UsernameNotFoundException("user Not found");

		UserAuthDetails userAuthDetails = user.get();
		
		ApplicationUserDetails aud = new 
				ApplicationUserDetails(userAuthDetails.getUsername(),userAuthDetails.getPassword());
		
		
		return aud;
	}

}
