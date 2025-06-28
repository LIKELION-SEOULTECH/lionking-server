package com.example.lionking.domain.project.service;

import com.example.lionking.domain.project.dto.request.MemberRetrospection;
import com.example.lionking.domain.project.dto.request.ProjectPostRequest;
import com.example.lionking.domain.project.dto.request.ProjectUpdateRequest;
import com.example.lionking.domain.project.dto.response.ProjectDetailResponse;
import com.example.lionking.domain.project.dto.response.ProjectResponse;
import com.example.lionking.domain.project.entity.*;
import com.example.lionking.domain.project.repository.ProjectImageRepository;
import com.example.lionking.domain.project.repository.ProjectRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.domain.project.repository.ProjectRetrospectionRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import com.example.lionking.global.s3.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ProjectRetrospectionRepository projectRetrospectionRepository;
    private final S3Service s3Service;

    @Transactional
    public void postProject(ProjectPostRequest req) {
        Project project = req.toEntity(); //요청 dto를 프로젝트 엔티티로
        connectProjectParticipation(project,req.getMemberIds());
        connectProjectImage(project,req.getThumbnailImageKey(), req.getLandingImagesKeys());
        connectMemberRetrospection(project,req.getMemberRetrospection());
        projectRepository.save(project); //저장 (Cascade로 ProjectParticipation, ProjectImage도 함께 저장)
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects(ProjectType projectType, Integer generation, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Project> projects;

        if (projectType != null && generation != null) {
            projects = projectRepository.findAllByProjectTypeAndGeneration(projectType, generation, pageable);
        } else if (projectType != null) {
            projects = projectRepository.findAllByProjectType(projectType, pageable);
        } else if (generation != null) {
            projects = projectRepository.findAllByGeneration(generation, pageable);
        } else {
            projects = projectRepository.findAll(pageable);
        }

        return projects.getContent().stream()
                .map(project -> ProjectResponse.from(project,s3Service))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDetailResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        return ProjectDetailResponse.from(project,s3Service);
    }

    @Transactional
    public void updateProject(Long projectId, ProjectUpdateRequest req) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // null 체크로 부분 업데이트
        project.update(req.getProjectName(),req.getProjectType(),req.getProjectDescription(),req.getVideoLink(),req.getGeneration());

        // 참여자 수정 (null이 아닐 때만)
        if (req.getMemberIds() != null) {
            updateProjectParticipations(project, req.getMemberIds());
        }

        if (req.getMemberRetrospection() != null) {
            updateProjectRetrospection(project,req.getMemberRetrospection());
        }

        // 이미지 수정 (썸네일 또는 랜딩 이미지가 있을 때만)
        if (req.getThumbnailImageKey() != null || req.getLandingImagesKeys() != null) {
            updateProjectImages(project, req.getThumbnailImageKey(), req.getLandingImagesKeys());
        }
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        deleteProjectImagesFromS3(project);
        projectRepository.delete(project);
    }

    private void deleteProjectImagesFromS3(Project project) {
        List<String> imageKeys = project.getProjectImages().stream()
                .map(ProjectImage::getS3Key)
                .filter(Objects::nonNull)  // null 체크
                .collect(Collectors.toList());

        if (!imageKeys.isEmpty()) {
            s3Service.deleteFiles(imageKeys);
            log.info("S3에서 이미지 {} 개 삭제 완료: {}", imageKeys.size(), imageKeys);
        } else {
            log.info("삭제할 S3 이미지가 없습니다.");
        }
    }

    private void updateProjectImages(Project project, String thumbnailImageKey, List<String> landingImagesKeys) {
        // 썸네일 이미지 수정
        if (thumbnailImageKey != null) {
            updateThumbnailImage(project, thumbnailImageKey);
        }

        // 랜딩 이미지 수정
        if (landingImagesKeys != null) {
            updateLandingImages(project, landingImagesKeys);
        }
    }

    private void updateLandingImages(Project project, List<String> landingImagesKeys) {
        // 1. 기존 랜딩 이미지들 찾기 및 삭제
        project.getProjectImages().removeIf(image -> {
            if (image.getImageType() == ImageType.LANDING) {
                s3Service.deleteFile(image.getS3Key());  // S3에서 삭제
                return true;  // 리스트에서 제거
            }
            return false;
        });

        // 2. 새 랜딩 이미지들 추가
        landingImagesKeys.stream()
                .map(key -> new ProjectImage(project, key, ImageType.LANDING))
                .forEach(project::addProjectImage);
    }
    // 썸네일 이미지만 수정

    private void updateThumbnailImage(Project project, String newThumbnailKey) {
        // 1. 기존 썸네일 이미지 찾기 및 삭제
        project.getProjectImages().removeIf(image -> {
            if (image.getImageType() == ImageType.THUMBNAIL) {
                s3Service.deleteFile(image.getS3Key());  // S3에서 삭제
                return true;  // 리스트에서 제거
            }
            return false;
        });

        // 2. 새 썸네일 이미지 추가
        ProjectImage newThumbnail = new ProjectImage(project, newThumbnailKey, ImageType.THUMBNAIL);
        project.addProjectImage(newThumbnail);
    }

    private void updateProjectRetrospection(Project project, List<MemberRetrospection> memberRetrospection) {
        project.getProjectRetrospections().clear();
        connectMemberRetrospection(project,memberRetrospection);
    }

    private void updateProjectParticipations(Project project, List<Long> memberIds) {
        // 기존 멤버 ID들
        List<Long> existingIds = project.getProjectParticipations().stream()
                .map(p -> p.getMember().getId())
                .collect(Collectors.toList());

        // 삭제: 기존에 있지만 새 요청에 없는 것들
        project.getProjectParticipations().removeIf(p ->
                !memberIds.contains(p.getMember().getId()));

        // 추가: 새 요청에 있지만 기존에 없는 것들
        List<Long> toAdd = memberIds.stream()
                .filter(id -> !existingIds.contains(id))
                .collect(Collectors.toList());

        // 기존 메서드 재사용
        if (!toAdd.isEmpty()) {
            connectProjectParticipation(project, toAdd);
        }
    }

    private void connectMemberRetrospection(Project project, List<MemberRetrospection> memberRetrospection) {
        // 멤버를 찾아서 그 멤버와 프로젝트 그리고 회고를 연관관계 맵핑해주고 프로젝트에 프로젝트 회고 add 해주기
        memberRetrospection.stream()
                .map(dto -> createRetrospection(project,dto))
                .forEach(project::addprojectRetrospection);
    }

    private ProjectRetrospection createRetrospection(Project project, MemberRetrospection dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
        ProjectRetrospection projectRetrospection = new ProjectRetrospection(member, project, dto.getRetrospection());
        return projectRetrospection;
    }

    private void connectProjectParticipation(Project project, List<Long> memberIds) {
        // dto로 받을때 미리 valid로 빈값 검사해주기 때문에 따로 검증 x
        // 한 번에 모든 Member 조회 (N+1 문제 방지)
        List<Member> members = memberRepository.findAllById(memberIds);

        if (members.size() != memberIds.size()) {
            throw new CustomException(GlobalErrorCode.NOT_FOUND);
        }

        members.stream()
                .map(member -> new ProjectParticipation(project, member))
                .forEach(project::addParticipation);
    }

    private void connectProjectImage(Project project, String thumbKey, List<String> landingKeys) {
        ProjectImage projectImage = new ProjectImage(project, thumbKey, ImageType.THUMBNAIL);
        project.addProjectImage(projectImage);

        landingKeys.stream()
                    .map(key -> new ProjectImage(project, key, ImageType.LANDING))
                    .forEach(project::addProjectImage);
    }
}
