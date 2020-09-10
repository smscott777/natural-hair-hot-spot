package com.nhhsgroup.naturalhairhotspot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.DTO.FavoriteProductDto;
import com.nhhsgroup.naturalhairhotspot.Service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	// Maps the POST request to register a new user. Returns message if successful.
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		userService.signUp(registerRequest);
		return new ResponseEntity<>("New User Registration Successful", HttpStatus.OK);
	}
	
	
	// Maps the PATCH request to add a product to the user's Favorite Products list
	@PatchMapping("/favoriteProduct")
	public void favoriteProduct(@RequestBody FavoriteProductDto favoriteProductDto) {
		userService.favoriteProduct(favoriteProductDto);
	}
}
