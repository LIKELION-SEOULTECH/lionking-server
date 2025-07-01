package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.blog.entitiy.BlogType;
import com.example.lionking.domain.media.dto.MediaResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        @Schema(description = "썸네일 이미지", example = "blogs/20250620-UUID-thumb.png")
        String thumbnailImage,
        @Schema(
                description = "미디어 리스트",
                example = "[{\"s3Key\": \"blogs/20250620-UUID-content1.png\", \"mediaType\": \"IMAGE\"}, {\"s3Key\": \"blogs/20250620-UUID-content2.png\", \"mediaType\": \"IMAGE\"}]"
        )
        List<MediaResponse> contentMedia,
        // 작성자 이름
        String MemberName,
        String position,
        @Schema(description = "작성일", example = "2025-07-01T14:30:00")
        LocalDate createdAt

) {
    public static BlogResponse from(Blog blog, List<MediaResponse> mediaList) {
        return new BlogResponse(
                blog.getId(),
                blog.getAuthor().getId(),
                blog.getBlogType(),
                blog.getTitle(),
                blog.getContent(),
                blog.getThumbnailImage(),
                mediaList,
                blog.getAuthor().getUsername(),
                blog.getAuthor().getPosition().name(),
                blog.getRegDate().toLocalDate()
        );
    }
}