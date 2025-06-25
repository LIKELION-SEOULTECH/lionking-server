package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.blog.entitiy.BlogType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BlogResponse(
        @Schema(description = "블로그 ID", example = "1")
        Long id,
        @Schema(description = "작성자 ID", example = "1")
        Long authorId,
        @Schema(description = "블로그 타입", example = "ARTICLE")
        BlogType blogType,
        @Schema(description = "제목", example = "이것은 제목이여!")
        String title,
        @Schema(description = "본문", example = "이것은 본문이여!")
        String content,
        @Schema(
                description = "이미지 리스트",
                example = "[{\"s3Key\": \"s3/image1.png\", \"imageType\": \"THUMBNAIL\"}, {\"s3Key\": \"s3/image2.png\", \"imageType\": \"CONTENT\"}]"
        )
        List<BlogImageResponse> images
) {
    public static BlogResponse from(Blog blog) {
        return new BlogResponse(
                blog.getId(),
                blog.getAuthor().getId(),
                blog.getBlogType(),
                blog.getTitle(),
                blog.getContent(),
                blog.getBlogImages().stream()
                        .map(BlogImageResponse::from)
                        .toList()
        );
    }
}
