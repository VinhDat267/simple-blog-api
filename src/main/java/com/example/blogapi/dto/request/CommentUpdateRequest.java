package com.example.blogapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateRequest {
    @NotBlank(message = "Comment không được bỏ trống.")
    private String body;
}
