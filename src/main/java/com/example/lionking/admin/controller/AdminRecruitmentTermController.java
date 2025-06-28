package com.example.lionking.admin.controller;

import com.example.lionking.domain.recruit.term.dto.RecruitmentTermRequest;
import com.example.lionking.domain.recruit.term.dto.RecruitmentTermResponse;
import com.example.lionking.domain.recruit.term.service.RecruitmentTermService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/term")
@RequiredArgsConstructor
@Tag(name = "Admin Term API", description = "관리자 : 모집 정보 관리 API")
@PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
public class AdminRecruitmentTermController {

    private final RecruitmentTermService recruitmentTermService;

    @PostMapping
    @Operation(summary = "모집 정보 등록", description = "모집 차수 및 일정 등록")
    public ApiResponse<RecruitmentTermResponse> create(@RequestBody RecruitmentTermRequest request) {
        RecruitmentTermResponse response = recruitmentTermService.create(request);
        return ApiResponse.success(response);
    }

    @GetMapping
    @Operation(summary = "모집 정보 목록 조회", description = "모든 모집 정보 및 기간을 조회")
    public ApiResponse<List<RecruitmentTermResponse>> getAll() {
        List<RecruitmentTermResponse> responses = recruitmentTermService.findAll();
        return ApiResponse.success(responses);
    }

    @PatchMapping("/{termId}")
    @Operation(summary = "모집 종료일 수정", description = "기존 모집 정보의 종료일을 수정")
    public ApiResponse<RecruitmentTermResponse> updateEndDate(
            @PathVariable Long termId,
            @RequestBody RecruitmentTermRequest request
    ) {
        RecruitmentTermResponse response = recruitmentTermService.updateEndDate(termId, request);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{termId}")
    @Operation(summary = "모집 정보 삭제", description = "모집 정보를 삭제")
    public ApiResponse<Object> delete(@PathVariable Long termId) {
        recruitmentTermService.delete(termId);
        return ApiResponse.success("모집 기수 삭제 완료");
    }

}