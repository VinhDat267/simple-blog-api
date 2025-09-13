package com.example.blogapi.service;

import org.springframework.stereotype.Service;

import com.example.blogapi.dto.PostCreateRequest;
import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.User;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post getPostById(Long id) {
        log.info("Finding post by ID: {}", id);
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với ID: " + id));
    }

    public Post createPost(PostCreateRequest request) {
        log.info("Creating post with '{}' for user ID {}", request.getTitle(), request.getUserId());

        User author = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + request.getUserId()));
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(author);
        return postRepository.save(post);
    }
}
