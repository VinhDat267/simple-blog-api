package com.example.blogapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapi.dto.request.PostCreateRequest;
import com.example.blogapi.dto.response.PostResponse;
import com.example.blogapi.dto.request.PostUpdateRequest;
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

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        log.info("Fetching all posts");
        return postRepository.findAll().stream().map(postMapper::toPostResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        log.info("Fetching post with ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy post với ID: " + id));

        return postMapper.toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        log.info("Updating post with ID: {}", id);
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy post với ID: " + id));
        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        Post updatedPost = postRepository.save(existingPost);

        return postMapper.toPostResponse(updatedPost);
    }

    @Transactional
    public void deletePost(Long id) {
        log.warn("Deleting post with ID: {}", id);
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy post với ID: " + id);
        }

        postRepository.deleteById(id);

        log.info("Post with ID: {} deleted successfully", id);
    }

}
