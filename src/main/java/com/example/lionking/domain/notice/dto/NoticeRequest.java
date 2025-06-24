package com.example.lionking.domain.notice.dto;

import com.example.lionking.domain.media.dto.MediaRequest;
import com.example.lionking.domain.notice.entity.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record NoticeRequest(
        @Schema(description = "공지 유형", example = "GENERAL")
        NoticeType noticeType,
        @Schema(description = "제목", example = "2025.06.25 정기 회의 안내")
        String title,
        @Schema(description = "내용", example = "2025년 6월 25일 1시 정기 회의가 있습니다.")
        String content,
        @Schema(
                description = "미디어 리스트",
                example = "[{\"s3Key\": \"blogs/20250620-UUID-content1.png\", \"mediaType\": \"IMAGE\"}, {\"s3Key\": \"blogs/20250620-UUID-content2.png\", \"mediaType\": \"IMAGE\"}]"
        )
        List<MediaRequest> contentMedia
) {}