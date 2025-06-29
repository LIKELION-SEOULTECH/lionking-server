package com.example.lionking.domain.project.dto.response;

import com.example.lionking.domain.project.entity.ImageType;
import com.example.lionking.domain.project.entity.Project;
import com.example.lionking.domain.project.entity.ProjectImage;
import com.example.lionking.domain.project.entity.ProjectType;
import com.example.lionking.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로젝트 조회 dto")
public class ProjectResponse {
    private Long id;
    private String thumbnailurl; //플젝 사진
    private String title; //플젝 제목
    private String description;
    private ProjectType projectType;
    private Integer generation;

    @Builder
    public ProjectResponse(Long id, String thumbnailurl, String title, String description, ProjectType projectType, Integer generation) {
        this.id = id;
        this.thumbnailurl = thumbnailurl;
        this.title = title;
        this.description = description;
        this.projectType = projectType;
        this.generation = generation;
    }

    public static ProjectResponse from(Project project, S3Service s3Service) {
        return ProjectResponse.builder()
                .id(project.getId())
                .thumbnailurl(s3Service.getPresignedUrlForGet(project.getThumbnailKey()))
                .title(project.getTitle())
                .description(project.getProjectDescription())
                .projectType(project.getProjectType())
                .generation(project.getGeneration())
                .build();
    }
}
