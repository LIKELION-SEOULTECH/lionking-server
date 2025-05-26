package com.example.lionking.domain.Project.controller;

import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.Project.service.ProjectService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    //프로젝트 등록
    @PostMapping("/{memberId}")
    @Operation(summary = "프로젝트 등록", description = "프로젝트 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> postProject(
            @RequestBody @Valid ProjectRequestDto projectRequestDto,
            @PathVariable Long memberId)
    {
        projectService.postProject(projectRequestDto,memberId);
        return ApiResponse.success("프로젝트 등록 성공");
    }


}
