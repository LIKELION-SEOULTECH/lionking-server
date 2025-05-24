package com.example.lionking.domain.Project.controller;

import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.Project.service.ProjectService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

/*
    //프로젝트 등록
    @PostMapping
    @Operation(summary = "프로젝트 등록", description = "프로젝트 등록 API")
    public ResponseEntity<ApiResponse<Void>> postProject(
            @RequestBody ProjectRequestDto projectRequestDto) {//valid는 일단 보류 리뷰 같은경우 null 일수도 있어서
        projectService.postProject(projectRequestDto)
    }
*/

}
