package com.example.blogapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateRequest {
    @NotBlank(message = "Comment không được bỏ trống.")
    private String body;
}
