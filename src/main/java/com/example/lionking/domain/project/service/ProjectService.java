package com.example.lionking.domain.project.service;

import com.example.lionking.domain.project.dto.*;
import com.example.lionking.domain.project.entity.*;
import com.example.lionking.domain.project.repository.ProjectImageRepository;
import com.example.lionking.domain.project.repository.ProjectRepository;
import com.example.lionking.domain.project.repository.ProjectReviewRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectReviewRepository projectReviewRepository;
    private final ProjectImageRepository projectImageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void postProject(ProjectRequestDto req) {
        Project project = toEntity(req);
        connectProjectImage(project,req.thumbnailImageKey(), req.landingImagesKeys());
        connectProjectParticipation(project,req.members());
        projectRepository.save(project); //저장 (Cascade로 ProjectParticipation, ProjectImage도 함께 저장)
    }

    private void connectProjectParticipation(Project project, @NotEmpty List<MemberReviewDto> memberReviewDtos) {
        memberReviewDtos.stream()
                .map(dto -> {
                    Member member = memberRepository.findById(dto.memberId())
                            .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
                    return ProjectParticipation.of(project,member, dto.review());
                })
                .forEach(project::addProjectParticipation);
    }

    private void connectProjectImage(Project project, @NotBlank String thumbKey, @NotEmpty List<String> landingKeys) {
        project.addProjectImage(ProjectImage.of(thumbKey, ImageType.THUMBNAIL));
        landingKeys.stream()
                .map(landingKey -> ProjectImage.of(landingKey, ImageType.LANDING))
                .forEach(project::addProjectImage);
    }

    private Project toEntity(ProjectRequestDto req) {
        return Project.builder()
                .title(req.title())
                .projectType(req.projectType())
                .projectDescription(req.projectDescription())
                .videoLink(req.videoLink())
                .generation(req.generation())
                .build();
    }

/*    public List<ProjectResponseDto> getAllProjects(ProjectType projectType, Integer generation, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        projectRepository.findProjects(projectType,generation,pageRequest);
    }*/

    public ProjectDetailResponseDto getProject(Long projectId) {
        return null;
    }

    public void updateProject(Long projectId, @Valid ProjectUpdateRequestDto req) {
        return;
    }

    public void deleteProject(Long projectId) {
        return;
    }
}
