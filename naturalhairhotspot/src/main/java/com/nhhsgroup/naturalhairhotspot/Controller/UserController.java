package com.nhhsgroup.naturalhairhotspot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhhsgroup.naturalhairhotspot.DTO.AuthenticationResponse;
import com.nhhsgroup.naturalhairhotspot.DTO.FavoriteProductDto;
import com.nhhsgroup.naturalhairhotspot.DTO.LoginRequest;
import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.Service.UserService;
import lombok.AllArgsConstructor;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	/**
	 * Maps the POST request to register a new user. Returns message if successful.
	 * @param registerRequest The form supplying an email, username, password,
	 * first name, and last name.
	 * @return A response entity indicating successful registration with a message
	 * and Http status code.
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		userService.signUp(registerRequest);
		return new ResponseEntity<>("New User Registration Successful", HttpStatus.OK);
	}
	
	/**
	 * Maps the POST request to log in a user.
	 * @param loginRequest The form supplying a username and password for authentication.
	 * @return The authentication response.
	 */
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}
	
	/**
	 *  Maps the POST request to add a product to the user's Favorite Products list.
	 *  Returns message if successful.
	 *  @param The form supplying a product number and username.
	 *  @return A response entity indicating successful registration with a message
	 *  and Http status code.
	 */
	@PostMapping("/favoriteProduct")
	public ResponseEntity<String> favoriteProduct(@RequestBody FavoriteProductDto favoriteProductDto) {
		userService.favoriteProduct(favoriteProductDto);
		return new ResponseEntity<>("Favorited Product Successfully: " + favoriteProductDto, HttpStatus.OK);
	}
}
