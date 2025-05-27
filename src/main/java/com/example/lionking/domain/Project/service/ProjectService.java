package com.example.lionking.domain.Project.service;

import com.example.lionking.domain.Project.dto.ProjectDetailResponseDto;
import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.Project.dto.ProjectResponseDto;
import com.example.lionking.domain.Project.dto.ProjectUpdateRequestDto;
import com.example.lionking.domain.Project.entity.*;
import com.example.lionking.domain.Project.repository.ProjectRepository;
import com.example.lionking.domain.Project.repository.ProjectReviewRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import com.example.lionking.global.s3.service.S3Service;
import jakarta.validation.Valid;
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
    private final MemberRepository memberRepository;
    private final ProjectReviewRepository projectReviewRepository;

    @Transactional
    public void postProject(ProjectRequestDto req) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        ProjectType projectType = ProjectType.transProjectType(req.projectType());
        String thumbnailImageKey = req.thumbnailImageKey();
        List<String> landingImagesKeys = req.landingImagesKeys();

        Project project = Project.create(req, projectType);

        // 연관관계 설정
        project.addProjectImage(ProjectImage.of(thumbnailImageKey, ImageType.THUMBNAIL));
        landingImagesKeys.forEach(imageKey ->
                project.addProjectImage(ProjectImage.of(imageKey, ImageType.LANDING))
        );
        project.addMember(member);

        projectRepository.save(project);

        ProjectParticipation projectParticipation = ProjectParticipation.of(project, member, req.review());
        project.getReviews().add(projectParticipation);
        member.getReviews().add(projectParticipation);

        projectReviewRepository.save(projectParticipation);
       /*아래처럼 하면 안되는 이유는 결국엔 해당 프로젝트의 멤버가 작성한 리뷰가 저장되어야 하는데 멤버가 작성한 마지막 리뷰가 저장되므로
         member.updateReview(req.review());*/
    }

    public List<ProjectResponseDto> getAllProjects(ProjectType projectType, Integer generation, Integer page, Integer size) {
        return null;
    }


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
