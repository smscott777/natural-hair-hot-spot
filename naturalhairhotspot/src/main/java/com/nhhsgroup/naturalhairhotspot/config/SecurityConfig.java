
package com.nhhsgroup.naturalhairhotspot.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	UserDetailsService userDetailsService;
	
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
	
		httpSecurity.httpBasic().and()	
					.csrf().disable()	// Allows for POST/DELETE requests to be authorized
					.cors()	// Allows cross-origin access for the front-end app
					.and()	
					.authorizeRequests(authorize -> authorize
					.mvcMatchers(HttpMethod.GET, "/api/v1/products/{prodNum}/users/**").denyAll()	// Denies anyone to view users nested in products
					.mvcMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()	// Authorizes anyone to view 
					.mvcMatchers(HttpMethod.GET, "/api/v1/categories").hasAnyRole("ADMIN", "USER")
					.mvcMatchers(HttpMethod.GET, "/api/v1/reviews/{id}/users/**").denyAll()		// Denies anyone to view users nested in reviews
					.mvcMatchers(HttpMethod.GET, "/api/v1/reviews/**").permitAll()		// Authorizes anyone to view all reviews
					.mvcMatchers(HttpMethod.GET, "/api/v1/users/{userId}/favoriteProducts").hasAnyRole("ADMIN", "USER")	
					.mvcMatchers(HttpMethod.GET, "/api/v1/users/{userId}/favoriteProducts").hasAnyRole("ADMIN", "USER")	
					.mvcMatchers(HttpMethod.GET, "/api/v1/auth/username").permitAll()	// Authorizes anyone to register a new account
					.mvcMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()	// Authorizes anyone to register a new account
					.mvcMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()	// Authorizes anyone to attempt to login
					.mvcMatchers(HttpMethod.POST, "/api/v1/auth/favoriteProduct").permitAll()//hasAnyRole("USER", "ADMIN")	// Authorizes users and admins to favorite a product
					.mvcMatchers(HttpMethod.POST, "/api/v1/reviews").permitAll()
					.mvcMatchers(HttpMethod.POST, "/api/v1/products").permitAll() // For populating the products database
					//.mvcMatchers(HttpMethod.POST, "/api/v1/reviews").hasAnyRole("ADMIN", "USER")	// Authorizes only users and admins to create a new review
					.mvcMatchers(HttpMethod.GET, "/api/v1/users/search/searchByReviews/**").permitAll()					
					.anyRequest().denyAll() )	// Denies any other requests not mentioned
					.logout()
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))//.permitAll()
					.logoutSuccessUrl("/api/v1/products");	
								
	}
	
	// Creates a Bean for the bCrypt password encoder that uses 1-way hashing to store passwords.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

