
package com.nhhsgroup.naturalhairhotspot.config;
 
import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
	private Filter jwtRequestFilter;
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	// Specifies authorities based on URL, user role, and Http method used.
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
	
		httpSecurity.csrf().disable()	// Allows for POST/DELETE requests to be authorized
					.cors()	// Allows cross-origin access for the front-end app
					.and()	
					.authorizeRequests(authorize -> {
						try {
							authorize
							.mvcMatchers(HttpMethod.GET, "/api/v1/products/{prodNum}/users/**").denyAll()	// Denies anyone to view users nested in products
							.mvcMatchers(HttpMethod.GET, "/api/v1/products/search/favoriteProducts").hasAnyRole("ADMIN", "USER")
							.mvcMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()	// Authorizes anyone to view
							.mvcMatchers(HttpMethod.POST, "/api/v1/products").hasAnyRole("ADMIN") // For populating the products database
							.mvcMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
							.mvcMatchers(HttpMethod.GET, "/api/v1/reviews/{id}/user/**").denyAll()		// Authorizes anyone to view all reviews
							.mvcMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll()		// Authorizes anyone to view all reviews
							.mvcMatchers(HttpMethod.POST, "/api/v1/reviews").permitAll()
							.mvcMatchers(HttpMethod.GET, "/api/v1/auth/user").hasAnyRole("ADMIN")
							.mvcMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()	// Authorizes anyone to register a new account
							.mvcMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()	// Authorizes anyone to attempt to login
							.mvcMatchers(HttpMethod.POST, "/api/v1/auth/favoriteProduct").hasAnyRole("USER", "ADMIN")	// Authorizes users and admins to favorite a product
							.mvcMatchers(HttpMethod.DELETE, "/api/v1/auth/favoriteProduct").hasAnyRole("USER", "ADMIN")	// Authorizes users and admins to delete a fav product
							.mvcMatchers(HttpMethod.GET, "/api/v1/auth/logout").permitAll()
							.anyRequest().denyAll()	// Denies any other requests not mentioned
							.and()
							.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
							.and()
							.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Authorization error");
						}
					});				
	}
	
	// Creates a Bean for the bCrypt password encoder that uses 1-way hashing to store passwords.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}