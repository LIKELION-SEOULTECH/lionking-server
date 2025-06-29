package com.example.lionking.domain.apply.question.controller;

import com.example.lionking.domain.apply.question.dto.QuestionResponse;
import com.example.lionking.domain.recruit.term.entitiy.RecruitTarget;
import com.example.lionking.domain.apply.question.service.QuestionService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
@Tag(name = "Question API", description = "지원서 질문 조회 API")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{termId}")
    @Operation(
            summary = "질문 목록 조회",
            description = "특정 모집의 질문 목록을 조회"
    )
    public ApiResponse<List<QuestionResponse>> getQuestions(
            @Parameter(description = "모집 정보 ID", example = "1")
            @PathVariable("termId") Long recruitmentTermId
    ) {
        List<QuestionResponse> responses = questionService.getQuestions(recruitmentTermId);
        return ApiResponse.success(responses);
    }
}
