package com.nhhsgroup.naturalhairhotspot.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.DTO.AuthenticationResponse;
import com.nhhsgroup.naturalhairhotspot.DTO.FavoriteProductDto;
import com.nhhsgroup.naturalhairhotspot.DTO.LoginRequest;
import com.nhhsgroup.naturalhairhotspot.Entity.Product;
import com.nhhsgroup.naturalhairhotspot.Entity.User;
import com.nhhsgroup.naturalhairhotspot.Repository.ProductRepository;
import com.nhhsgroup.naturalhairhotspot.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Transactional			//For interacting w/ relational database
@AllArgsConstructor		//Injects thru constructors, rather than fields using @Autowired
@Service
public class UserService {
	
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private final AuthenticationManager authenticationManager;

	/**
	 * Method to save a new user.
	 * @param registerRequest The form supplying an email, username, password,
	 * first name, and last name.
	 */
	public void signUp(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEnabled(true);
		userRepository.save(user);	
	}
	
	/**
	 * Authenticates a user using username/password authenication.
	 * @param loginRequest The form supplying a username and password for authentication.
	 * @return The authentication response.
	 */
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return AuthenticationResponse.builder()
        		.username(loginRequest.getUsername())
        		.build();	
	}
	
	/**
	 *  Method to add a product to a "Favorite Products" list for a specific user
	 * @param favoriteProductDto The form supplying a product number and username.
	 */
	public void favoriteProduct(FavoriteProductDto favoriteProductDto) {		
		Product newProduct = productRepository.findByProdNum(favoriteProductDto.getProductProdNum());	// select the new product to add
		Optional<User> optionalUser = userRepository.findByUsername(favoriteProductDto.getUsername());	// select the user 
		User user = optionalUser.get();	// get the user as a User object, not optional
		List<Product> favoriteProductsList = user.getFavoriteProducts();	// get the user's current FavProducts list
		favoriteProductsList.add(newProduct);	// currently deletes the current list then replaces it with new list
												// results in granting old entries new id's as well as the new entry
		userRepository.save(user);	
	}
}
