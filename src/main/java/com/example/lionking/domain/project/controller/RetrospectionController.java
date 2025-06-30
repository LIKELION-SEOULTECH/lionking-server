package com.example.lionking.domain.project.controller;

import com.example.lionking.domain.project.dto.response.RetrospectionResponse;
import com.example.lionking.domain.project.service.ProjectRetrospectionService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/retrospentions")
@Tag(name = "프로젝트 회고 API", description = "프로젝트 회고 관련 API")
public class RetrospectionController {
    private final ProjectRetrospectionService projectRetrospectionService;

    @Operation(summary = "멤버가 프로젝트에 작성한 회고 조회")
    @GetMapping("/{projectId}/{memberId}")
    public ApiResponse<RetrospectionResponse> getRetrospections(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId
    ) {
        RetrospectionResponse retrospections = projectRetrospectionService.getRetrospections(projectId, memberId);
        return ApiResponse.success(retrospections);
    }
}
