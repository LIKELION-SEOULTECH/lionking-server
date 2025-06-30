package com.example.lionking.domain.project.controller;

import com.example.lionking.domain.project.dto.request.ProjectPostRequest;
import com.example.lionking.domain.project.dto.request.ProjectUpdateRequest;
import com.example.lionking.domain.project.dto.response.ProjectDetailResponse;
import com.example.lionking.domain.project.dto.response.ProjectResponse;
import com.example.lionking.domain.project.entity.ProjectType;
import com.example.lionking.domain.project.service.ProjectService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
@Tag(name = "프로젝트 API", description = "프로젝트 등록 관련 API")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 등록", description = "프로젝트 등록 API")
    @PostMapping
    //@PreAuthorize("hasRole('REPRESENTATIVE') or hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> postProject(@RequestBody @Valid ProjectPostRequest req) {
        projectService.postProject(req);
        return ApiResponse.success("프로젝트 등록 성공");
    }

    @Operation(summary = "(기수 활동) 모든 프로젝트 조회", description = "모든 프로젝트 조회 API")
    @GetMapping
    public ApiResponse<List<ProjectResponse>> getAllProjects(
            @RequestParam(required = false) ProjectType projectType,
            @RequestParam(required = false) Integer generation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        List<ProjectResponse> Projects = projectService.getAllProjects(projectType, generation, page, size);
        return ApiResponse.success(Projects);
    }

    @Operation(summary = "단일 프로젝트 조회", description = "단일 프로젝트 조회 API")
    @GetMapping("/{projectId}")
    public ApiResponse<ProjectDetailResponse> getProject(@PathVariable Long projectId) {
        ProjectDetailResponse project = projectService.getProject(projectId);
        return ApiResponse.success(project);
    }

    @Operation(summary = "단일 프로젝트 수정", description = "단일 프로젝트 수정 API")
    @PatchMapping("/{projectId}")
    //@PreAuthorize("hasRole('REPRESENTATIVE') or hasRole('MANAGER')")
    public ApiResponse<Object> updateProject(
            @PathVariable("projectId") Long projectId,
            @RequestBody @Valid ProjectUpdateRequest req)
    {
        projectService.updateProject(projectId,req);
        return ApiResponse.success("프로젝트 수정 성공");
    }

    @Operation(summary = "프로젝트 삭제", description = "단일 프로젝트 삭제 API")
    @DeleteMapping("/{projectId}")
    //@PreAuthorize("hasRole('REPRESENTATIVE') or hasRole('MANAGER')")
    public ApiResponse<Object> deleteProject(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.success("프로젝트 삭제 성공");
    }

    @Operation(summary = "멤버가 참여한 프로젝트")

    @Operation(summary = "멤버가 프로젝트에 작성한 회고")

}
