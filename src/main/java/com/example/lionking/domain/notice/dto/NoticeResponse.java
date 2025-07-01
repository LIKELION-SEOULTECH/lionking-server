package com.example.lionking.domain.notice.dto;

import com.example.lionking.domain.media.dto.MediaResponse;
import com.example.lionking.domain.notice.entity.Notice;
import com.example.lionking.domain.notice.entity.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record NoticeResponse(
        @Schema(description = "공지 ID", example = "1")
        Long id,
        @Schema(description = "작성자 ID", example = "1")
        Long authorId,
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
        List<MediaResponse> contentMedia,
        @Schema(description = "작성일", example = "2025-07-01T14:30:00")
        LocalDate createdAt
) {
    public static NoticeResponse from(Notice notice, List<MediaResponse> contentMedia) {
        return new NoticeResponse(
                notice.getId(),
                notice.getAuthor().getId(),
                notice.getNoticeType(),
                notice.getTitle(),
                notice.getContent(),
                contentMedia,
                notice.getRegDate().toLocalDate()
        );
    }
}