package com.nhhsgroup.naturalhairhotspot.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.nhhsgroup.naturalhairhotspot.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	
	// Returns a list of products by category
	@RestResource(path="categoryid")
	Page<Product> findByCategoryId(@Param("id") int id, Pageable page);
	
	// Returns a list of products by name containing
	@RestResource(path="searchbykeyword")
	Page<Product> findByNameContaining(@Param("name") String keyword,  Pageable page);
	
	// Returns a list of products containing an ingredient
	@RestResource(path="searchByIngredients")
	Page<Product> findByIngredientsContaining(@Param("ingredient") String ingredients, Pageable page);
	
	// Finds a product by the product number
	Product findByProdNum(int prodNum);
	
	// Returns a list of products favorited by the given user
	@RestResource(path="favoriteProducts")
	List<Product> findByUsersUsername(@Param("username")String username);
	
}
