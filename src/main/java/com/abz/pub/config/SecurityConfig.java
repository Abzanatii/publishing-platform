package com.abz.pub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abz.pub.domain.entities.User;
import com.abz.pub.repos.UserRepo;
import com.abz.pub.security.JwtAuthenticationFilter;
import com.abz.pub.security.pubUserDetailsService;
import com.abz.pub.services.AuthenticationService;

@Configuration 
public class SecurityConfig {

    @Bean 
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return  new JwtAuthenticationFilter(authenticationService);
    }

    @Bean 
    public UserDetailsService userDetailsService(UserRepo userRepo){
        pubUserDetailsService ppubUserDetailsService = new pubUserDetailsService(userRepo);

        String email = "user@test.com";
        userRepo.findByEmail(email).orElseGet(() -> {
            User newuser = User.builder()
                .name("test user")
                .email(email)
                .password(passwordEncoder().encode("password"))
                .build(); 
            return userRepo.save(newuser);
        });

        return ppubUserDetailsService;
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts/drafts").authenticated()
                .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/tags/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/tags/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/tags/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
