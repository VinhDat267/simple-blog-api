package com.example.blogapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapi.dto.CommentCreateRequest;
import com.example.blogapi.dto.CommentResponse;
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

    @Transactional
    public CommentResponse createComment(Long postId, Long userId, CommentCreateRequest request) {
        log.info("Creating comment for post ID {} by user ID {}", postId, userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + postId));

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        Comment comment = new Comment();
        comment.setBody(request.getBody());

        comment.setPost(post);
        author.addComment(comment);

        Comment savedComment = commentRepository.save(comment);

        return mapToCommentResponse(savedComment);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setBody(comment.getBody());
        response.setCreatedAt(comment.getCreatedAt());
        response.setAuthorUsername(comment.getAuthor().getUsername());
        response.setPostId(comment.getPost().getId());
        return response;
    }
}
