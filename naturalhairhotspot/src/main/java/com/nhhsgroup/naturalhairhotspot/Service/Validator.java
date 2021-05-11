package com.nhhsgroup.naturalhairhotspot.Service;

import org.springframework.stereotype.Service;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;

@Service
public class Validator {

	public String validate(RegisterRequest registerRequest) {
		String email = registerRequest.getEmail();
		String username = registerRequest.getUsername();
		String firstName = registerRequest.getFirstName();
		String lastName = registerRequest.getLastName();
		String password = registerRequest.getPassword();
		
		if(email.isEmpty()) {
			return "Email can not be blank.";
		}
		else if(!email.contains("@")) {
			return "Not a valid email address.";
		}
		else if(username.isEmpty()) {
			return "Username can not be blank.";
		}
		else if(firstName.isEmpty()) {
			return "First name can not be blank.";
		}
		else if(lastName.isEmpty()) {
			return "Last name can not be blank.";
		}
		else if(password.isEmpty()) {
			return "Password can not be blank.";
		}
		else {
			return "New User Registration Successful";
		}
	}
}
