package com.example.lionking.domain.apply.question.dto;

import com.example.lionking.domain.apply.question.entitiy.Question;
import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionResponse(
        @Schema(description = "질문 ID", example = "1")
        Long id,
        @Schema(description = "질문 내용", example = "자기소개를 해주세요.")
        String content,
        @Schema(description = "질문 순서", example = "1")
        Integer sequence
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getContent(),
                question.getSequence()
        );
    }
}