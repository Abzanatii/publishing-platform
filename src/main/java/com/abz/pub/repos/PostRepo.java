package com.abz.pub.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abz.pub.domain.entities.Post;

//@Repository 
public interface PostRepo extends JpaRepository<Post, UUID>{


    
}
