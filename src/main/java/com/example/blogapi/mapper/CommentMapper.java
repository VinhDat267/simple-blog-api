package com.example.blogapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.blogapi.dto.CommentResponse;
import com.example.blogapi.entity.Comment;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CommentMapper {
    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "post.id", target = "postId")
    CommentResponse toCommentResponse(Comment comment);
}
