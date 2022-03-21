package com.nhhsgroup.naturalhairhotspot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.nhhsgroup.naturalhairhotspot.Entity.Review;

@RepositoryRestResource(collectionResourceRel="reviews", path="reviews")
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	// returns a list of reviews by product number
	@RestResource(path = "prodNum")
	Page<Review> findByProductProdNum(@Param("prodNum") int prodNum, Pageable page);	
}