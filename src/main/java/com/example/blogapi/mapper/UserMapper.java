package com.example.blogapi.mapper;

import org.mapstruct.Mapper;

import com.example.blogapi.dto.UserResponse;
import com.example.blogapi.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
