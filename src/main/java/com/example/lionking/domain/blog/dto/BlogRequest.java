package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.BlogType;
import com.example.lionking.domain.media.dto.MediaRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BlogRequest(
        @Schema(description = "블로그 타입", example = "ARTICLE")
        BlogType blogType,
        @Schema(description = "제목", example = "이것은 제목이여!")
        String title,
        @Schema(description = "본문", example = "이것은 본문이여!")
        String content,
        @Schema(description = "썸네일 이미지", example = "blogs/20250620-UUID-thumb.png")
        String thumbnailImage
) {
}