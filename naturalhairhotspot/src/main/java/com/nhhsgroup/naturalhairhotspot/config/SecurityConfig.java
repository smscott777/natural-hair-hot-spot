
package com.nhhsgroup.naturalhairhotspot.config;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	// Specifies authorities based on URL, user role, and Http method used.
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
	
		httpSecurity.httpBasic().and()	
					.csrf().disable()	// Allows for POST/DELETE requests to be authorized
					.cors().and()	// Allows cross-origin access for the front-end app
					.authorizeRequests(authorize -> authorize
					.mvcMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()	// Authorizes anyone to view all products
					.mvcMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()	// Authorizes anyone to register a new account
					.mvcMatchers(HttpMethod.POST, "/api/v1/reviews").hasAnyRole("ADMIN", "USER")	// Authorizes only users and admins to create a new review
					.mvcMatchers("/api/v1/reviews/**").permitAll()		// Authorizes anyone to view all reviews
					.antMatchers(HttpMethod.POST).hasRole("ADMIN")	// Authorizes only admins to make any post requests other than those specified above
					.anyRequest().denyAll() );	// Denies any other requests not mentioned
					
	}
	
	// Creates a Bean for the bCrypt password encoder that uses 1-way hashing to store passwords.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

