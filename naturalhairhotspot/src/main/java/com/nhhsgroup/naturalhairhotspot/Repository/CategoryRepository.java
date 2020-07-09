package com.nhhsgroup.naturalhairhotspot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nhhsgroup.naturalhairhotspot.Entity.Category;

@RepositoryRestResource(collectionResourceRel="categories", path="categories")
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
