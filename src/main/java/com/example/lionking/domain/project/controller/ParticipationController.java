package com.example.lionking.domain.project.controller;

import com.example.lionking.domain.project.dto.response.ParticipationProjectResponse;
import com.example.lionking.domain.project.entity.ProjectParticipation;
import com.example.lionking.domain.project.service.ProjectParticipationService;
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
@RequestMapping("/api/v1/participation")
@Tag(name = "프로젝트 참여자 관련 API")
public class ParticipationController {
    private final ProjectParticipationService projectParticipationService;

    @Operation(summary = "멤버가 참여한 프로젝트 ID와 갯수 반환 API")
    @GetMapping("/{memberId}")
    public ApiResponse<ParticipationProjectResponse> getParticipationProject(
            @PathVariable("memberId") Long memberId
    ) {
        ParticipationProjectResponse participationProject = projectParticipationService.getParticipationProject(memberId);
        return ApiResponse.success(participationProject);
    }

}
