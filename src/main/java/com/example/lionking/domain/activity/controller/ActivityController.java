package com.example.lionking.domain.activity.controller;

import com.example.lionking.domain.activity.dto.ActivityResponse;
import com.example.lionking.domain.activity.service.ActivityService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
@Tag(name = "Activity API", description = "활동기록 조회")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "전체 활동기록 조회", description = "등록된 모든 활동기록을 조회")
    @GetMapping
    public ApiResponse<List<ActivityResponse>> getAllActivities() {
        return ApiResponse.success(activityService.getAllActivities(), "활동기록 전체 조회 성공");
    }

    @Operation(summary = "활동기록 상세 조회", description = "ID에 해당하는 활동기록 상세정보 조회")
    @GetMapping("/{activityId}")
    public ApiResponse<ActivityResponse> getActivity(@PathVariable Long activityId) {
        return ApiResponse.success(activityService.getActivity(activityId), "활동기록 상세 조회 성공");
    }

}