package com.example.lionking.domain.recruit.subscribe.controller;

import com.example.lionking.domain.recruit.subscribe.dto.RecruitSubscribeRequest;
import com.example.lionking.domain.recruit.subscribe.dto.RecruitSubscribeResponse;
import com.example.lionking.domain.recruit.subscribe.service.RecruitSubscribeService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
@Tag(name = "Recruit Subscribe API", description = "모집 알림 신청")
public class RecruitSubscribeController {

    private final RecruitSubscribeService recruitSubscribeService;

    @PostMapping("/subscribe")
    @Operation(summary = "모집 알림신청", description = "모집 알림 신청자의 이메일 저장")
    public ApiResponse<Object> subscribe(
            @Valid @RequestBody
            RecruitSubscribeRequest request
    ) {
        recruitSubscribeService.subscribe(request.email());
        return ApiResponse.success("신청 완료 : 모집 시작 시 메일 전송 예정입니다.");
    }

    /**
     * 운영진 권한 이상 허용 API
     * TODO : 추후 관리자 컨트롤러 분리 예정
     */
    @Operation(summary = "모집 알림 신청자 리스트 조회", description = "등록된 모든 모집 알림 신청자 조회 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @GetMapping()
    public ApiResponse<List<RecruitSubscribeResponse>> getAllSubscribers() {
        List<RecruitSubscribeResponse> responses = recruitSubscribeService.getAllSubscribers();
        return ApiResponse.success(responses, "모든 모집 알림 신청자 조회 성공");
    }

}