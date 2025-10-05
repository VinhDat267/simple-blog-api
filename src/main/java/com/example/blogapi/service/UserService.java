package com.example.blogapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapi.config.JwtProperties;
import com.example.blogapi.dto.UserCreateRequest;
import com.example.blogapi.entity.User;
import com.example.blogapi.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserCreateRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    @PostConstruct
    public void printJwtConfig() {
        log.info("JWT Secret Key: {}", jwtProperties.getSecretKey());
        log.info("JWT Expiration Time (ms): {}", jwtProperties.getExpirationMs());
    }
}
