package com.example.lionking.domain.apply.answer.controller;

import com.example.lionking.domain.apply.answer.dto.AnswerRequest;
import com.example.lionking.domain.apply.answer.service.AnswerService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
@Tag(name = "Answer API", description = "지원서 답변 API")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{memberId}")
    @Operation(summary = "지원서 제출 및 수정", description = "특정 멤버가 답변을 제출 또는 수정")
    public ApiResponse<Object> submitAnswers(
            @PathVariable Long memberId,
            @RequestBody AnswerRequest request
    ) {
        answerService.submitAnswers(memberId, request);
        return ApiResponse.success("지원서 제출 완료 : 마감일까지 수정이 가능합니다.");
    }

}