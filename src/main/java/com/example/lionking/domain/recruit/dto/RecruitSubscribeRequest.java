package com.example.lionking.domain.recruit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "모집 알림 신청 요청")
public record RecruitSubscribeRequest(
        @Schema(description = "신청자 이메일", example = "user@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        String email
) {
}