package com.example.lionking.domain.Project.service;

import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.Project.entity.*;
import com.example.lionking.domain.Project.repository.ProjectRepository;
import com.example.lionking.domain.Project.repository.ProjectReviewRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import com.example.lionking.global.s3.service.S3Service;
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
    private final S3Service s3Service;
    private final ProjectReviewRepository projectReviewRepository;

    @Transactional
    public void postProject(ProjectRequestDto projectRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
       /*아래처럼 하면 안되는 이유는 결국엔 해당 프로젝트의 멤버가 작성한 리뷰가 저장되어야 하는데 멤버가 작성한 마지막 리뷰가 저장되므로
         member.updateReview(projectRequestDto.review());*/
        // 프로젝트 타입을 받으면 string 이니까 이걸 서비스 코드에서 어떤 프로젝트 타입의 enum인지 먼저 확인해주고 넣어주기
        ProjectType projectType = ProjectType.transProjectType(projectRequestDto.projectType());
        String thumbnailImageKey = projectRequestDto.thumbnailImageKey();
        List<String> landingImagesKeys = projectRequestDto.landingImagesKeys();

        Project project = Project.create(projectRequestDto, projectType);

        // 연관관계 설정
        project.addProjectImage(ProjectImage.of(thumbnailImageKey, ImageType.THUMBNAIL));
        landingImagesKeys.forEach(imageKey ->
                project.addProjectImage(ProjectImage.of(imageKey, ImageType.LANDING))
        );
        project.addMember(member);

        projectRepository.save(project);

        ProjectReview projectReview = ProjectReview.of(project, member, projectRequestDto.review());
        project.getReviews().add(projectReview);
        member.getReviews().add(projectReview);

        projectReviewRepository.save(projectReview);

        /*
        if (thumbnailImageKey != null) {
            ProjectImage thumbnail = ProjectImage.create(thumbnailImageKey, ImageType.THUMBNAIL);
            project.addProjectImage(thumbnail);
        }*/

        /*
        if (landingImagesKeys != null) {
            for (String landingKey : landingImagesKeys) {
                ProjectImage landingImage = ProjectImage.create(landingKey, ImageType.LANDING);
                project.addProjectImage(landingImage);
            }
        }*/

        // 프로젝트 썸네일은 ProjectImage의
        // s3Service.getPresignedUrlForPut() // 이거는 저장하는 api 따로 파서 거기서 실행하고
        // 반환 받은 이미지 s3key만 받아서 저장해주기
    }
}
