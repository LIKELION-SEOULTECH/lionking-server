package com.example.lionking.domain.media.dto;

import com.example.lionking.domain.media.entity.Media;
import com.example.lionking.domain.media.entity.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미디어 등록 응답 DTO")
public record MediaResponse(
        @Schema(description = "Media 식별자", example = "1")
        Long id,
        @Schema(description = "S3 Key", example = "blogs/20240522-UUID-content.png")
        String s3Key,
        @Schema(description = "미디어 타입", example = "IMAGE")
        MediaType mediaType
) {
    public static MediaResponse from(Media media) {
        return new MediaResponse(media.getId(), media.getS3Key(), media.getMediaType());
    }
}