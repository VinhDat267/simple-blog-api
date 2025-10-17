package com.example.blogapi.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorUsername;

}
