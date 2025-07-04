package com.example.lionking.domain.blog.dto;

import com.example.lionking.domain.blog.entitiy.BlogImageType;
import io.swagger.v3.oas.annotations.media.Schema;

public record BlogImageRequest(
        @Schema(description = "S3 Key", example = "s3/image2.png")
        String s3Key,
        @Schema(description = "이미지 타입", example = "CONTENT")
        BlogImageType imageType
) {
}
