package com.nhhsgroup.naturalhairhotspot.Controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * and Http status code. This body of this response entity is the value of 'data' 
	 * on the front end app.
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		String message;
		message = userService.signUp(registerRequest);
		if(message.equals("New User Registration Successful")) {
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
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
		String message;
		message = userService.favoriteProduct(favoriteProductDto);
		
		if(message.equals("success")) {
			return new ResponseEntity<>("Saved product.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Product already saved.", HttpStatus.BAD_REQUEST);
		}	
	}
	
	/**
	 * Maps the DELETE request to delete a product from the user's Favorite Products list.
	 * @param productProdNum The product number of the product to be deleted.
	 * @param username The username of the user whose list to alter.
	 * @return A response entity indicating successful deletion.
	 */
	@RestResource	// Angular client accepts params, not a body on Delete.
	@DeleteMapping("/favoriteProduct")
	public ResponseEntity<String> deleteFavProduct(@Param("productProdNum") String productProdNum, @Param("username") String username) {
		int intProdNum = Integer.parseInt(productProdNum);
		
		userService.deleteFavProduct(intProdNum, username);
		
		return new ResponseEntity<>("Deleted product.", HttpStatus.OK);
	}
	
	/**
	 * Maps the GET request to retrieve the principal user if they are reachable.
	 * Represents a way to check if the user is in fact logged in.
	 * @param user The principal.
	 * @return The principal.
	 */
	@GetMapping("/user")
	public Principal user(Principal user) {
		if(user != null) {
			System.out.println("Name: " + user.getName());
		}
		else {
			System.out.println("Principal user is null");
		}
		return user;
	}
	
	/**
	 * Maps the GET request to logout a user.
	 * @param request The HttpServlet request.
	 * @param response The HttpServlet response.
	 */
	@GetMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		userService.logout(request, response);
	}	
}
