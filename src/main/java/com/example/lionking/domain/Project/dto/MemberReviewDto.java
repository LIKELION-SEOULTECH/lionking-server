package com.example.lionking.domain.Project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberReviewDto(
        @NotNull Long memberId,
        @NotBlank String review
) {}
