package com.nhhsgroup.naturalhairhotspot.Service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

@Transactional			// For interacting w/ relational database
@AllArgsConstructor		// Injects thru constructors, rather than fields using @Autowired
@Service
public class UserService {
	
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private Validator validator;
	private AuthenticationManager authenticationManager;
	private JwtTokenService jwtTokenService;
	private UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * Method to save a new user.
	 * @param registerRequest The form supplying an email, username, password,
	 * first name, and last name.
	 */
	public String signUp(RegisterRequest registerRequest) {
		String validationResponse = "";	
		validationResponse = validator.validate(registerRequest);
		
		if (validationResponse.equals("New User Registration Successful")) {
			User user = new User();
			user.setEmail(registerRequest.getEmail());
			user.setUsername(registerRequest.getUsername());
			user.setFirstName(registerRequest.getFirstName());
			user.setLastName(registerRequest.getLastName());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			user.setEnabled(true);
			user.setRoles("ROLE_USER");
			userRepository.save(user);
			return validationResponse;
		}	
		else return validationResponse;
	}
	
	/**
	 * Authenticates a user using username/password authentication.
	 * @param loginRequest The form supplying a username and password for authentication.
	 * @return The authentication response.
	 */
	public AuthenticationResponse login(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()));        
        
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));
        
        return authenticationResponse;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		try {
			for(Cookie cookie : request.getCookies()) {
				cookie.setMaxAge(0);
			}
		} catch (Exception e) {
			System.out.println("No cookies.");
		}
	}
	
	
	/**
	 * Method to add a product to a "Favorite Products" list for a specific user.
	 * @param favoriteProductDto The form supplying a product number and username.
	 */
	public String favoriteProduct(FavoriteProductDto favoriteProductDto) {		
		
		Product newProduct = productRepository.findByProdNum(favoriteProductDto.getProductProdNum());	// select the new product to add
		Optional<User> optionalUser = userRepository.findByUsername(favoriteProductDto.getUsername());	// select the user 
		User user = optionalUser.get();	// get the user as a User object, not optional
		List<Product> favoriteProductsList = user.getFavoriteProducts();	// get the user's current FavProducts list
		
		String validationResponse = validator.validate(favoriteProductsList, favoriteProductDto);
		
		if(validationResponse.equals("success")) {
			favoriteProductsList.add(newProduct);	
													
			userRepository.save(user);
			return validationResponse;
		}
		else {
			return validationResponse;
		}	
	}
	
	/**
	 * Method to delete a product from a "Favorite Products" list for a specific user.
	 * @param prodNum The product number of the product to be deleted.
	 * @param username The username of the user whose list to alter.
	 */
	public void deleteFavProduct(int prodNum, String username) {
		Product product = productRepository.findByProdNum(prodNum);	// select the new product to delete
		Optional<User> optionalUser = userRepository.findByUsername(username);	// select the user 
		User user = optionalUser.get();	// get the user as a User object, not optional
		List<Product> favoriteProductsList = user.getFavoriteProducts();	// get the user's current FavProducts list
		
		favoriteProductsList.remove(product);
	}
}