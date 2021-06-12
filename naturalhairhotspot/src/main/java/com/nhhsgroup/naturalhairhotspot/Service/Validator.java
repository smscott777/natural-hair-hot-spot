package com.nhhsgroup.naturalhairhotspot.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhhsgroup.naturalhairhotspot.DTO.FavoriteProductDto;
import com.nhhsgroup.naturalhairhotspot.DTO.RegisterRequest;
import com.nhhsgroup.naturalhairhotspot.Entity.Product;
import com.nhhsgroup.naturalhairhotspot.Repository.ProductRepository;
import com.nhhsgroup.naturalhairhotspot.Repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Validator {
	
	private ProductRepository productRepository;
	private UserRepository userRepository;

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
		else if(userRepository.findByUsername(username) != null) {
			return "Username not available.";
		}
		else {
			return "New User Registration Successful";
		}
	}

	public String validate(List<Product> favoriteProductsList, FavoriteProductDto favoriteProductDto) {
		// Prevents duplicate favorite products.
		int prodNum = favoriteProductDto.getProductProdNum();
		Product product = productRepository.findByProdNum(prodNum);
				
		if(favoriteProductsList.contains(product)) {
			return "fail";
		}
		else {
			return "success";
		}
		
	}
}
