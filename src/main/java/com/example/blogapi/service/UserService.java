package com.example.blogapi.service;

import org.springframework.stereotype.Service;

import com.example.blogapi.dto.UserCreateRequest;
import com.example.blogapi.entity.User;
import com.example.blogapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserCreateRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }
}
