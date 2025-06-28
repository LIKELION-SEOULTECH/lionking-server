package com.example.lionking.domain.recruit.term.dto;


import com.example.lionking.domain.recruit.term.entitiy.RecruitTarget;
import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "모집 기수 응답")
public record RecruitmentTermResponse(
        @Schema(description = "ID", example = "1") Long id,
        @Schema(description = "기수", example = "13") int generation,
        @Schema(description = "모집 대상", example = "MEMBER") RecruitTarget target,
        @Schema(description = "시작일", example = "2025-07-01") LocalDate startDate,
        @Schema(description = "종료일", example = "2025-07-14") LocalDate endDate
) {
    public static RecruitmentTermResponse from(RecruitmentTerm term) {
        return new RecruitmentTermResponse(
                term.getId(),
                term.getGeneration(),
                term.getTarget(),
                term.getStartDate(),
                term.getEndDate()
        );
    }
}