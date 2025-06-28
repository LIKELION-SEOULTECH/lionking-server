package com.example.lionking.domain.apply.answer.controller;

import com.example.lionking.domain.apply.answer.dto.AnswerRequest;
import com.example.lionking.domain.apply.answer.dto.AnswerResponse;
import com.example.lionking.domain.apply.answer.service.AnswerService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
@Tag(name = "Answer API", description = "지원서 답변 API")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/{memberId}/{termId}")
    @Operation(summary = "지원서 조회", description = "특정 멤버가 특정 모집 기수에 작성한 지원서 조회")
    public ApiResponse<List<AnswerResponse>> getAnswers(
            @PathVariable Long memberId,
            @PathVariable Long termId
    ) {
        List<AnswerResponse> answers = answerService.getAnswersByMemberAndTerm(memberId, termId);
        return ApiResponse.success(answers);
    }

    @PostMapping("/{memberId}")
    @Operation(summary = "지원서 제출", description = "특정 멤버가 답변을 제출 또는 수정")
    public ApiResponse<Object> submitAnswers(
            @PathVariable Long memberId,
            @RequestBody AnswerRequest request
    ) {
        answerService.submitAnswers(memberId, request);
        return ApiResponse.success("답변 제출 완료");
    }

}