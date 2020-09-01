package com.nhhsgroup.naturalhairhotspot.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.DTO.FavoriteProductDto;
import com.nhhsgroup.naturalhairhotspot.Entity.Product;
import com.nhhsgroup.naturalhairhotspot.Entity.User;
import com.nhhsgroup.naturalhairhotspot.Repository.ProductRepository;
import com.nhhsgroup.naturalhairhotspot.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Transactional			//For interacting w/ relational database
@AllArgsConstructor		//Injects thru constructors, not fields (which Autowired does)
@Service
public class UserService {
	
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private ProductRepository productRepository;

	// Method to save a new user with input credentials.
	public void signUp(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getUsername());
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		//user.setPassword(registerRequest.getPassword());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		//user.setEnabled(false);
		
		userRepository.save(user);
		
	}
	
	
	// Method to add a product to a "Favorite Products" list for a specific user
	public void favoriteProduct(FavoriteProductDto favoriteProductDto) {		
		Product newProduct = productRepository.findByProdNum(favoriteProductDto.getProductProdNum());
		User user = userRepository.findByUsername(favoriteProductDto.getUsername());
		List<Product> favoriteProductsList = user.getFavoriteProducts();
		favoriteProductsList.add(newProduct);
		
		userRepository.save(user);
	}
}
