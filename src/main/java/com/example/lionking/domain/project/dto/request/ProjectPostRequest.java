package com.example.lionking.domain.project.dto.request;

import com.example.lionking.domain.project.entity.Project;
import com.example.lionking.domain.project.entity.ProjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로젝트 등록 요청 dto")
public class ProjectPostRequest {
    @NotBlank
    private String projectName; //플젝 이름

    @NotNull
    @Schema(
            description = "프로젝트 유형",
            example = "IDEATHON(아이디어톤), CENTRAL_HACKATHON(중앙해커톤), UNION_HACKATHON(연합해커톤), LONG_TERM(장기프로젝트), ETC(기타)"
    )
    private ProjectType projectType; //플젝 유형

    @NotNull
    private Integer generation; //플젝 기수

    @NotBlank
    private String projectDescription; //플젝 소개

    @NotNull
    private String videoLink; //시연영상링크

    @NotNull
    private List<MemberRetrospection> memberRetrospection;

    @NotNull
    private List<Long> memberIds;

    @NotNull
    private String thumbnailImageKey;

    @NotNull
    private List<String> landingImagesKeys;

    public Project toEntity() {
        return Project.builder()
                .title(projectName)
                .projectType(projectType)
                .projectDescription(projectDescription)
                .videoLink(videoLink)
                .generation(generation)
                .build();
    }
}
