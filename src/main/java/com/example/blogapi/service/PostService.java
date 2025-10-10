package com.example.blogapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapi.dto.PostCreateRequest;
import com.example.blogapi.dto.PostResponse;
import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.User;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.mapper.PostMapper;
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
    private final PostMapper postMapper;

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        log.info("Creating post for user ID {}", request.getUserId());

        User author = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + request.getUserId()));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        author.addPost(post);
        Post savePosted = postRepository.save(post);

        log.info("Post created successfully with ID: {}", savePosted.getId());

        return postMapper.toPostResponse(savePosted);

    }

}
