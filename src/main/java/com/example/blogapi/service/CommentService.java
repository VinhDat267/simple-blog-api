package com.example.blogapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapi.dto.CommentCreateRequest;
import com.example.blogapi.dto.CommentResponse;
import com.example.blogapi.dto.CommentUpdateRequest;
import com.example.blogapi.entity.Comment;
import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.User;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.mapper.CommentMapper;
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
    private final CommentMapper commentMapper;

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

        return commentMapper.toCommentResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Không tìm thấy post với ID: " + postId);
        }

        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(commentMapper::toCommentResponse).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, CommentUpdateRequest request) {

        log.info("Updating  comment ID {} for post ID {}", commentId, postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy post với ID: " + postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy comment với ID " + commentId));

        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Bình luận không thuộc về bài viết này");
        }

        comment.setBody(request.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(updatedComment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        log.warn("Deleting comment ID {} from post ID {}", commentId, postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bình luận với ID: " + commentId));
        if (!comment.getPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Bình luận không thuộc về bài viết này.");
        }

        commentRepository.delete(comment);
        log.info("Comment with ID {} deleted successfully", commentId);
    }
}
