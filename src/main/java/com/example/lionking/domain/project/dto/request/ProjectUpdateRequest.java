package com.example.lionking.domain.project.dto.request;

import com.example.lionking.domain.project.entity.ProjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectUpdateRequest {
    private String projectName; //플젝 이름
    private ProjectType projectType; //플젝 유형
    private Integer generation; //플젝 기수
    private String projectDescription; //플젝 소개
    private String videoLink; //시연영상링크
    private List<Long> memberIds; //플젝 참가 멤버 id
    private List<MemberRetrospection> memberRetrospection;
    private String thumbnailImageKey;
    private List<String> landingImagesKeys;
}
