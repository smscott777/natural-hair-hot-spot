package com.nhhsgroup.naturalhairhotspot.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhhsgroup.naturalhairhotspot.DTO.UserDetailsImpl;
import com.nhhsgroup.naturalhairhotspot.Entity.User;
import com.nhhsgroup.naturalhairhotspot.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		
		Optional<User> user = userRepository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("No user " + 
					"Found with username: " + username));
		
		return user.map(UserDetailsImpl::new).get();
	}
}
