package com.abz.pub.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abz.pub.domain.entities.User;
import com.abz.pub.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
public class pubUserDetailsService implements UserDetailsService{

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new pubUserDetails(user);
    }

}
