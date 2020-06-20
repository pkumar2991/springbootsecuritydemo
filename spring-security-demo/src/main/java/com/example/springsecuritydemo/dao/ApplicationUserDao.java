package com.example.springsecuritydemo.dao;

import java.util.Optional;

import com.example.springsecuritydemo.model.ApplicationUser;

public interface ApplicationUserDao {
	public Optional<ApplicationUser> fetchApplicationUserByUsername(String username);
}
