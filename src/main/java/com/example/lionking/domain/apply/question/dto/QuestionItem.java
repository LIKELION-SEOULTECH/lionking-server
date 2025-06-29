package com.example.lionking.domain.apply.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionItem(
        @Schema(description = "질문 내용", example = "자기소개를 해주세요.")
        String content,
        @Schema(description = "질문 순서", example = "1")
        Integer sequence
) {
}