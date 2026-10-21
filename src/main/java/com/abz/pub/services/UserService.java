package com.abz.pub.services;

import java.util.UUID;

import com.abz.pub.domain.entities.User;

public interface UserService {
    User getUserById(UUID id);
}
