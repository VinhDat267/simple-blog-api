package com.example.blogapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostUpdateRequest {

    @NotBlank(message = "Tiêu đề không được trống.")
    @Size(min = 5, max = 100, message = "Tiêu đề phải có độ dài từ 5 đến 100 ký tự.")
    private String title;

    @NotBlank(message = "Content không được trống.")
    private String content;
}
