package com.example.springsecuritydemo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.springsecuritydemo.model.ApplicationUser;
import com.example.springsecuritydemo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoImpl implements ApplicationUserDao{
	
	private final PasswordEncoder passwordEndoder;
	
	@Autowired
	public FakeApplicationUserDaoImpl(PasswordEncoder passwordEndoder) {
		this.passwordEndoder = passwordEndoder;
	}

	@Override
	public Optional<ApplicationUser> fetchApplicationUserByUsername(String username) {
		System.out.println(getApplicationUsers());
		Optional<ApplicationUser> user = getApplicationUsers()
				.stream()
				.filter(applicationUser -> {
					return username.equals(applicationUser.getUsername());
				}).findFirst();
		
		return user;
	}

	public List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
			new ApplicationUser(
					ApplicationUserRole.STUDENT.getGrantedAuthorities(),
					"annasmith", 
					passwordEndoder.encode("password"), 
					true,
					true,
					true, 
					true),
			new ApplicationUser(
					ApplicationUserRole.ADMIN.getGrantedAuthorities(),
					"linda", 
					passwordEndoder.encode("password"), 
					true,
					true,
					true, 
					true),
			new ApplicationUser(
					ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
					"tom", 
					passwordEndoder.encode("password"), 
					true,
					true,
					true, 
					true)
		);
		return applicationUsers;
	}
}
