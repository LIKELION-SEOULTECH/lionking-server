package com.example.lionking.domain.recruit.subscribe.dto;

import com.example.lionking.domain.recruit.subscribe.entity.RecruitSubscribe;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모집 알림 신청 응답")
public record RecruitSubscribeResponse(
        @Schema(description = "ID", example = "1")
        Long id,
        @Schema(description = "이메일", example = "user@example.com")
        String email,
        @Schema(description = "모집 알림 이메일 전송 여부", example = "false")
        Boolean isNotified
) {
    public static RecruitSubscribeResponse from(RecruitSubscribe subscribe) {
        return new RecruitSubscribeResponse(
                subscribe.getId(),
                subscribe.getEmail(),
                subscribe.getIsNotified()
        );
    }
}