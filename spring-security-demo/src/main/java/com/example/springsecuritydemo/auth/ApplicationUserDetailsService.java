package com.example.springsecuritydemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsecuritydemo.dao.ApplicationUserDao;

@Service
public class ApplicationUserDetailsService implements UserDetailsService{
	
	private final ApplicationUserDao applicationuUserDao;

	@Autowired
	public ApplicationUserDetailsService(@Qualifier("fake") ApplicationUserDao applicationuUserDao) {
		this.applicationuUserDao = applicationuUserDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return applicationuUserDao.fetchApplicationUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));
	}

}
