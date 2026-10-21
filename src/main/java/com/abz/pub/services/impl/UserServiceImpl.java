package com.abz.pub.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.abz.pub.domain.entities.User;
import com.abz.pub.repos.UserRepo;
import com.abz.pub.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserServiceImpl implements UserService{
    
    private final UserRepo userRepo;
    
    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

}
