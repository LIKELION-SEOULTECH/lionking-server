package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.BlogType;
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
        String thumbnailImage,
        @Schema(
                description = "미디어 리스트",
                example = "[{\"s3Key\": \"blogs/20250620-UUID-content1.png\", \"mediaType\": \"IMAGE\"}, {\"s3Key\": \"blogs/20250620-UUID-content2.png\", \"mediaType\": \"IMAGE\"}]"
        )
        List<BlogMediaRequest> contentMedia
) {
}
