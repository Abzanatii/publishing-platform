package com.abz.pub.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abz.pub.domain.entities.Category;

//@Repository 
public interface CategoryRepo extends JpaRepository<Category, UUID>{
    @Query("SELECT c from Category c Left Join fetch c.posts")
    List<Category> findAllWithPostCount();

    boolean existsByNameIgnoreCase(String name);
}
