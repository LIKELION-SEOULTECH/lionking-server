package com.example.lionking.domain.apply.answer.dto;

public record AnswerResponse(
        Long questionId,
        String questionContent,
        Integer sequence,
        String answerContent
) {}