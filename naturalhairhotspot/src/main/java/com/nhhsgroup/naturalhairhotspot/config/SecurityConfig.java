
package com.nhhsgroup.naturalhairhotspot.config;
 

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
 
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable().httpBasic().and()
					.authorizeRequests()
						.antMatchers(HttpMethod.POST, "/auth/signup").hasAnyRole()
						.antMatchers(HttpMethod.POST, "/products").hasAnyRole();
					
	}

}

