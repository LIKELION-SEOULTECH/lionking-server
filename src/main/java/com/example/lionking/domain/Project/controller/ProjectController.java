package com.example.lionking.domain.Project.controller;

import com.example.lionking.domain.Project.dto.ProjectDetailResponseDto;
import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.Project.dto.ProjectResponseDto;
import com.example.lionking.domain.Project.dto.ProjectUpdateRequestDto;
import com.example.lionking.domain.Project.entity.ProjectType;
import com.example.lionking.domain.Project.service.ProjectService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 등록", description = "프로젝트 등록 API")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> postProject(@RequestBody @Valid ProjectRequestDto req)
    {
        projectService.postProject(req);
        return ApiResponse.success("프로젝트 등록 성공");
    }

    @Operation(summary = "모든 프로젝트 조회", description = "모든 프로젝트 조회 API")
    @GetMapping
    public ApiResponse<List<ProjectResponseDto>> getAllProjects(
            @RequestParam(required = false) ProjectType projectType,
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    )
    {
        List<ProjectResponseDto> Projects = projectService.getAllProjects(projectType, generation, page, size);
        return ApiResponse.success(Projects);
    }

    @Operation(summary = "단일 프로젝트 조회", description = "단일 프로젝트 조회 API")
    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailResponseDto> getProject(@PathVariable Long projectId)
    {
        ProjectDetailResponseDto project = projectService.getProject(projectId);
        return ApiResponse.success(project);
    }

    @Operation(summary = "단일 프로젝트 수정", description = "단일 프로젝트 수정 API")
    @PatchMapping("/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Object> updateProject(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid ProjectUpdateRequestDto req)
    {
        projectService.updateProject(projectId,req);
        return ApiResponse.success("프로젝트 수정 성공");
    }

    @Operation(summary = "단일 프로젝트 삭제", description = "단일 프로젝트 삭제 API")
    @DeleteMapping("/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Object> deleteProject(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.success("프로젝트 삭제 성공");
    }
}
