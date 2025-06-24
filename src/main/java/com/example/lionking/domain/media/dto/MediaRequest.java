package com.example.lionking.domain.media.dto;

import com.example.lionking.domain.media.entity.MediaOwner;
import com.example.lionking.domain.media.entity.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미디어 등록 요청 DTO")
public record MediaRequest(
        @Schema(description = "S3 Key", example = "blogs/20240522-UUID-content.png")
        String s3Key,
        @Schema(description = "미디어 타입", example = "IMAGE")
        MediaType mediaType,
        @Schema(description = "도메인 타입", example = "BLOG")
        MediaOwner mediaOwner,
        @Schema(description = "도메인 ID", example = "1")
        Long ownerId
) {
}
