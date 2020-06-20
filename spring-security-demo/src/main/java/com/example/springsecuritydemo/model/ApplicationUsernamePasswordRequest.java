package com.example.springsecuritydemo.model;

public class ApplicationUsernamePasswordRequest {

	private String username;
	private String password;
	
	public ApplicationUsernamePasswordRequest() {
	}

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
	
	
}
