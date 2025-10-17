package com.example.blogapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 5, max = 100, message = "Tiêu đề phải có độ dài từ 5 đến 100 ký tự")
    private String title;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @NotNull(message = "userId không được để trống")
    private Long userId;

}
