package com.abz.pub.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abz.pub.domain.entities.User;

//@Repository 
public interface UserRepo extends JpaRepository<User, UUID>{

}
