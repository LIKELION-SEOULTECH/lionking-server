package com.example.lionking.domain.recruit.term.dto;

import com.example.lionking.domain.recruit.term.entitiy.RecruitTarget;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "모집 기수 등록/수정 요청")
public record RecruitmentTermRequest(
        @Schema(description = "기수", example = "13") int generation,
        @Schema(description = "모집 대상", example = "MEMBER") RecruitTarget target,
        @Schema(description = "모집 시작일", example = "2025-07-01") LocalDate startDate,
        @Schema(description = "모집 종료일", example = "2025-07-14") LocalDate endDate
) {
}