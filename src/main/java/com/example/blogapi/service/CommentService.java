package com.example.blogapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blogapi.dto.CommentCreateRequest;
import com.example.blogapi.entity.Comment;
import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.User;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.repository.CommentRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Comment> getCommentByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + postId));
        return commentRepository.findByPostId(postId);
    }

    public Comment creatComment(Long postId, Long userId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + postId));

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        Comment comment = new Comment();
        comment.setBody(request.getBody());
        comment.setAuthor(author);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Không tìm thấy comment cần xoá với ID: " + commentId));
        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Comment không thuộc về bài viết này.");
        }

        commentRepository.delete(comment);
    }

}
