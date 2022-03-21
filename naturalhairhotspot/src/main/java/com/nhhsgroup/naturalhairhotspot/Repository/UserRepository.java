
package com.nhhsgroup.naturalhairhotspot.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import com.nhhsgroup.naturalhairhotspot.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	
	@RestResource(path = "searchByReviews")
	User findByReviewsId(@Param("id") int id);
}