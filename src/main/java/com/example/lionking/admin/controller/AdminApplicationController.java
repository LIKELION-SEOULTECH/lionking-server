package com.example.lionking.admin.controller;

import com.example.lionking.domain.apply.answer.dto.AnswerResponse;
import com.example.lionking.domain.apply.answer.service.AnswerService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/application")
@RequiredArgsConstructor
@Tag(name = "Admin Application API", description = "관리자 : 지원서 조회 API")
public class AdminApplicationController {

    private final AnswerService answerService;

    @GetMapping("/{termId}/{memberId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @Operation(summary = "지원서 조회", description = "특정 멤버가 특정 모집 기수에 작성한 지원서 조회")
    public ApiResponse<List<AnswerResponse>> getAnswers(
            @PathVariable Long termId,
            @PathVariable Long memberId
    ) {
        List<AnswerResponse> answers = answerService.getAnswersByMemberAndTerm(memberId, termId);
        return ApiResponse.success(answers);
    }
}
