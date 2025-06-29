package com.example.lionking.admin.controller;

import com.example.lionking.domain.apply.question.dto.QuestionRequest;
import com.example.lionking.domain.apply.question.dto.QuestionResponse;
import com.example.lionking.domain.apply.question.service.QuestionService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/question")
@RequiredArgsConstructor
@Tag(name = "Admin Question API", description = "관리자 : 지원서 질문 관리 API")
public class AdminQuestionController {

    private final QuestionService questionService;

    @PutMapping()
    @Operation(summary = "질문 목록 등록/갱신", description = "질문 목록 등록 + 전체 덮어쓰기")
    public ApiResponse<List<QuestionResponse>> register(
            @RequestBody QuestionRequest request
    ) {
        return ApiResponse.success(questionService.register(request));
    }

}