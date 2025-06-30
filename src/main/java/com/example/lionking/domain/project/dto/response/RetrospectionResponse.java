package com.example.lionking.domain.project.dto.response;

import com.example.lionking.domain.project.entity.ProjectRetrospection;
import lombok.Builder;

public class RetrospectionResponse {
    private Long projectId;
    private Long memberId;
    private String retrospection;

    @Builder
    public RetrospectionResponse(Long projectId, Long memberId, String retrospection) {
        this.projectId = projectId;
        this.memberId = memberId;
        this.retrospection = retrospection;
    }

    public static RetrospectionResponse from(ProjectRetrospection projectRetrospection) {
        return RetrospectionResponse.builder()
                .projectId(projectRetrospection.getProject().getId())
                .memberId(projectRetrospection.getMember().getId())
                .retrospection(projectRetrospection.getContent())
                .build();
    }
}
