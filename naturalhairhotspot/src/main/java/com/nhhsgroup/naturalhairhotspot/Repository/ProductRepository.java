package com.nhhsgroup.naturalhairhotspot.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.nhhsgroup.naturalhairhotspot.Entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	
	// returns a list of products by category
	@RestResource(path="categoryid")
	Page<Product> findByCategoryId(@Param("id") int id, Pageable page);
	
	// returns a list of products by name containing
	@RestResource(path="searchbykeyword")
	Page<Product> findByNameContaining(@Param("name") String keyword,  Pageable page);
	
	// returns a list of products containing an ingredient
	@RestResource(path="searchByIngredients")
	Page<Product> findByIngredientsContaining(@Param("ingredient") String ingredients, Pageable page);
	
	
}
