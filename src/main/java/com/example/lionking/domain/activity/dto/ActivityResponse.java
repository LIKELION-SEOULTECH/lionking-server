package com.example.lionking.domain.activity.dto;

import com.example.lionking.domain.activity.entity.Activity;
import com.example.lionking.domain.media.dto.MediaResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ActivityResponse(
        @Schema(description = "활동기록 ID", example = "1")
        Long id,
        @Schema(description = "작성자 ID", example = "1")
        Long authorId,
        @Schema(description = "제목", example = "2025.06.26 MT 교류회")
        String title,
        @Schema(description = "내용", example = "2025년 6월 26일 10시 MT 교류회 참석")
        String content,
        @Schema(
                description = "미디어 리스트",
                example = "[{\"s3Key\": \"blogs/20250620-UUID-content1.png\", \"mediaType\": \"IMAGE\"}, {\"s3Key\": \"blogs/20250620-UUID-content2.png\", \"mediaType\": \"IMAGE\"}]"
        )
        List<MediaResponse> contentMedia
) {
    public static ActivityResponse from(Activity activity, List<MediaResponse> contentMedia) {
        return new ActivityResponse(
                activity.getId(),
                activity.getAuthor().getId(),
                activity.getTitle(),
                activity.getContent(),
                contentMedia
        );
    }
}