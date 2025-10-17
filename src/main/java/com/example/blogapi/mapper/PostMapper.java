package com.example.blogapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.blogapi.dto.response.PostResponse;
import com.example.blogapi.entity.Post;

@Mapper(componentModel = "spring", uses = { UserMapper.class, CommentMapper.class })
public interface PostMapper {
    @Mapping(source = "user.username", target = "authorUsername")
    PostResponse toPostResponse(Post post);
}
