package com.example.springsecuritydemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.springsecuritydemo.auth.ApplicationUserDetailsService;
import com.example.springsecuritydemo.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.example.springsecuritydemo.jwt.JwtConfig;
import com.example.springsecuritydemo.jwt.JwtTokenVerifier;

import static com.example.springsecuritydemo.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import static com.example.springsecuritydemo.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserDetailsService applicationUserDetailService;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	@Autowired
	public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
			ApplicationUserDetailsService applicationUserDetailService, JwtConfig jwtConfig,
			SecretKey secretKey) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.applicationUserDetailService = applicationUserDetailService;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				// .and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
				.addFilterAfter(new JwtTokenVerifier(jwtConfig,secretKey),JwtUsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/", "index").permitAll().antMatchers("/api/**").hasRole(STUDENT.name())
				.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
				.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
				.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermissions())
				.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
				.anyRequest()
				.authenticated();
		
//				.httpBasic(); // Basic Auth
//				.formLogin() // Enables Form based authentication
//					.loginPage("/login")// Map the default Login URI to this
//					.usernameParameter("username") // name parameter used for username name tag
//					.passwordParameter("password") // password parameter used for password name tag
//				.permitAll()
//				.defaultSuccessUrl("/courses",true)
//				.and()
//				.rememberMe()
//					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
//					.key("secret")
//					.rememberMeParameter("remember-me") // remember me parameter for remeber me name tag
//				.and()
//				.logout()
//					.logoutUrl("/logout")
//					.clearAuthentication(true)
//					.invalidateHttpSession(true)
//					.deleteCookies("JSESSIONID","remember-me")
//					.logoutSuccessUrl("/login");
	}

	

	/*
	 * @Override
	 * 
	 * @Bean protected UserDetailsService userDetailsService() { UserDetails
	 * annaSmith =
	 * User.builder().username("annasmith").password(passwordEncoder.encode(
	 * "password")) // .roles(ApplicationUserRole.STUDENT.name())
	 * .authorities(STUDENT.getGrantedAuthorities()).build();
	 * 
	 * UserDetails linda =
	 * User.builder().username("linda").password(passwordEncoder.encode("password"))
	 * // .roles(ApplicationUserRole.ADMIN.name())
	 * .authorities(ADMIN.getGrantedAuthorities()).build();
	 * 
	 * UserDetails tom =
	 * User.builder().username("tom").password(passwordEncoder.encode("password"))
	 * // .roles(ApplicationUserRole.ADMINTRAINEE.name())
	 * .authorities(ADMINTRAINEE.getGrantedAuthorities()).build(); return new
	 * InMemoryUserDetailsManager(annaSmith, linda, tom); }
	 */

	/**
	 * To implement user authentication from Database
	 * Step 1: Create DAOAuthentication Provider
	 * Step 2: Override the configure having Authentication Builder.
	 */
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(applicationUserDetailService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthenticationProvider());
	}
}
