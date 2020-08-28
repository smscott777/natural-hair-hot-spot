
package com.nhhsgroup.naturalhairhotspot.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.nhhsgroup.naturalhairhotspot.Entity.User;

@Repository//RestResource(collectionResourceRel="user", path="user")
public interface UserRepository extends JpaRepository<User, Integer>{
	/*
	@RestResource(path="signUp")
	void save(@Param("email") String email, @Param("password") String password, 
				@Param("firstName") String firstName, @Param("lastName") String lastName);
	
	@RestResource(path="searchUserByEmail")
	Page<User> findByEmail(@Param("email") String email, Pageable page);
	*/
	Optional<User> findByUsername(String username);
	
}
