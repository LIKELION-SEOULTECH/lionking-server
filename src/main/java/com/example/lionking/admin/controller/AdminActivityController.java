package com.example.lionking.admin.controller;

import com.example.lionking.domain.activity.dto.ActivityRequest;
import com.example.lionking.domain.activity.dto.ActivityResponse;
import com.example.lionking.domain.activity.service.ActivityService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/activity")
@RequiredArgsConstructor
@Tag(name = "Admin Activity API", description = "관리자 : 활동기록 API")
public class AdminActivityController {

    private final ActivityService activityService;

    @Operation(summary = "활동기록 등록", description = "활동기록 등록 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @PostMapping("/{authorId}")
    public ApiResponse<ActivityResponse> postActivity(
            @PathVariable Long authorId,
            @Valid @RequestBody ActivityRequest request
    ) {
        ActivityResponse response = activityService.postActivity(request, authorId);
        return ApiResponse.success(response, "활동기록 등록 성공");
    }

    @Operation(summary = "활동기록 수정", description = "활동기록 수정 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @PatchMapping("/{activityId}")
    public ApiResponse<ActivityResponse> updateActivity(
            @PathVariable Long activityId,
            @Valid @RequestBody ActivityRequest request
    ) {
        ActivityResponse response = activityService.updateActivity(activityId, request);
        return ApiResponse.success(response, "활동기록 수정 성공");
    }

    @Operation(summary = "활동기록 삭제", description = "활동기록 삭제 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @DeleteMapping("/{activityId}")
    public ApiResponse<Object> deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ApiResponse.success("활동기록 삭제 성공");
    }

}