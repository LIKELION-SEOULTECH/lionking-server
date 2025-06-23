package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.BlogMedia;
import com.example.lionking.domain.blog.entitiy.BlogMediaType;
import io.swagger.v3.oas.annotations.media.Schema;

public record BlogMediaResponse(
        @Schema(description = "S3 Key", example = "blogs/20240522-UUID-content.png")
        String s3Key,
        @Schema(description = "미디어 타입", example = "IMAGE")
        BlogMediaType mediaType
) {
    public static BlogMediaResponse from(BlogMedia image) {
        return new BlogMediaResponse(image.getS3Key(), image.getMediaType());
    }
}