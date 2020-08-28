package com.nhhsgroup.naturalhairhotspot.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.Entity.User;
import com.nhhsgroup.naturalhairhotspot.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Transactional			//For interacting w/ relational database
@AllArgsConstructor		//Injects thru constructors, not fields (which Autowired does)
@Service
public class UserService {
	
//	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	// Method to save a new user with input credentials.
	public void signUp(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setPassword(registerRequest.getPassword());
		//user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		//user.setEnabled(false);
		
		userRepository.save(user);
		
	}
}
