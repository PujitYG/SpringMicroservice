package com.microservice.authservice.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
public class UserAuthDetails {
	
	@Id
	private String username;
	
	
	
	public UserAuthDetails() {
		super();
	}

	public UserAuthDetails(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="EMAIL")
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserAuthDetails [username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	
	
	

}
