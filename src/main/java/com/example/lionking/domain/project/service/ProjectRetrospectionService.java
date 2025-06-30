package com.example.lionking.domain.project.service;

import com.example.lionking.domain.project.dto.response.RetrospectionResponse;
import com.example.lionking.domain.project.entity.ProjectRetrospection;
import com.example.lionking.domain.project.repository.ProjectRetrospectionRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectRetrospectionService {
    private final ProjectRetrospectionRepository projectRetrospectionRepository;

    public RetrospectionResponse getRetrospections(Long projectId, Long memberId) {
        ProjectRetrospection projectRetrospection = projectRetrospectionRepository.findByMemberIdAndProjectId(projectId, memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
        return RetrospectionResponse.from(projectRetrospection);
    }
}
