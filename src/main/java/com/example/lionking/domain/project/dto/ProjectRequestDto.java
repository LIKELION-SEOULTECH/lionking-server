package com.example.lionking.domain.project.dto;

import com.example.lionking.domain.project.entity.ProjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "프로젝트 등록 요청 dto")
public record ProjectRequestDto(
        @NotBlank String title,

        @Schema(
                description = "프로젝트 유형",
                example = "IDEATHON(아이디어톤), CENTRAL_HACKATHON(중앙해커톤), UNION_HACKATHON(연합해커톤), LONG_TERM(장기프로젝트), ETC(기타)"
        )
        @NotNull ProjectType projectType,

        @NotBlank String projectDescription,

        @NotBlank String thumbnailImageKey,

        @NotEmpty List<String> landingImagesKeys,
        String videoLink,

        @NotEmpty List<MemberReviewDto> members,
        String generation // 어떤 기수의 프로젝트인지 받는 폼 있어야 할거 같음
) {}
