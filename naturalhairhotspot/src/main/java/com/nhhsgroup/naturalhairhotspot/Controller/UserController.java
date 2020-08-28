package com.nhhsgroup.naturalhairhotspot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.Service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	// Maps the POST request to register a new user. Returns message if successful.
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		userService.signUp(registerRequest);
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}
}
