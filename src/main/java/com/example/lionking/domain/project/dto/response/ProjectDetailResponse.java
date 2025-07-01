package com.example.lionking.domain.project.dto.response;

import com.example.lionking.domain.project.dto.ParticipationDetailDto;
import com.example.lionking.domain.project.entity.Project;
import com.example.lionking.domain.project.entity.ProjectParticipation;
import com.example.lionking.domain.project.entity.ProjectRetrospection;
import com.example.lionking.domain.project.entity.ProjectType;
import com.example.lionking.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Schema(description = "프로젝트 단일 조회 dto")
public class ProjectDetailResponse {
    private Long id;
    private String title;
    private String description;
    private String videoLink;
    private Integer generation;
    private ProjectType projectType;
    private String thumbnail;
    private List<ParticipationDetailDto> participations;
    private List<String> landingImages;
    private List<Map<String, String>> retrospections;

    @Builder
    public ProjectDetailResponse(Long id, String title, String description, String videoLink, Integer generation, ProjectType projectType, String thumbnail, List<ParticipationDetailDto> participations, List<String> landingImages, List<Map<String, String>> retrospections) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoLink = videoLink;
        this.generation = generation;
        this.projectType = projectType;
        this.thumbnail = thumbnail;
        this.participations = participations;
        this.landingImages = landingImages;
        this.retrospections = retrospections;
    }

    public static ProjectDetailResponse from(Project project, S3Service s3Service) {
        return ProjectDetailResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getProjectDescription())
                .videoLink(project.getVideoLink())
                .generation(project.getGeneration())
                .projectType(project.getProjectType())
                .thumbnail(s3Service.getPresignedUrlForGet(project.getThumbnailKey()))
                .landingImages(createLandingImages(project.getLandingKeys(), s3Service))
                .participations(createParticipationDetails(project.getProjectParticipations(), project.getProjectRetrospections(), s3Service))
                .retrospections(createRetrospection(project.getProjectRetrospections()))
                .build();
    }

    // 새로 완성된 메서드
    private static List<ParticipationDetailDto> createParticipationDetails(
            List<ProjectParticipation> projectParticipations,
            List<ProjectRetrospection> projectRetrospections,
            S3Service s3Service
    ) {
        // 회고를 memberId 기준으로 Map화
        Map<Long, String> retrospectionMap = projectRetrospections.stream()
                .collect(Collectors.toMap(
                        r -> r.getMember().getId(),
                        ProjectRetrospection::getContent,
                        (existing, replacement) -> existing
                ));

        return projectParticipations.stream()
                .map(participation -> ParticipationDetailDto.builder()
                        .memberId(participation.getMember().getId())
                        .username(participation.getMember().getUsername())
                        .profileImage(s3Service.getPresignedUrlForGet(participation.getMember().getProfileImage()))
                        .position(participation.getMember().getPosition().name())
                        .role(participation.getMember().getRole().name())
                        .retrospection(retrospectionMap.getOrDefault(participation.getMember().getId(), null))
                        .build())
                .collect(Collectors.toList());
    }

    // 기존 메서드들 그대로 유지
    private static List<String> findParticipations(List<ProjectParticipation> projectParticipations) {
        return projectParticipations.stream()
                .map(projectParticipation -> projectParticipation.getMember().getUsername())
                .collect(Collectors.toList());
    }

    private static List<String> createLandingImages(List<String> landingKeys, S3Service s3Service) {
        return landingKeys.stream()
                .map(key->s3Service.getPresignedUrlForGet(key))
                .collect(Collectors.toList());
    }

    private static List<Map<String, String>> createRetrospection(List<ProjectRetrospection> projectRetrospections) {
        return projectRetrospections.stream()
                .map(pr -> {
                    Map<String, String> retrospection = new HashMap<>();
                    retrospection.put("memberName", pr.getMember().getUsername());
                    retrospection.put("content", pr.getContent());
                    return retrospection;
                })
                .collect(Collectors.toList());
    }
}
