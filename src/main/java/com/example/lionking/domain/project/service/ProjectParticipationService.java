package com.example.lionking.domain.project.service;

import com.example.lionking.domain.project.dto.response.ParticipationProjectResponse;
import com.example.lionking.domain.project.entity.ProjectParticipation;
import com.example.lionking.domain.project.repository.ProjectParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectParticipationService {

    private final ProjectParticipationRepository projectParticipationRepository;

    public ParticipationProjectResponse getParticipationProject(Long memberId) {
        List<Long> projectIds = projectParticipationRepository.findProjectIdsByMemberId(memberId);
        int count = projectParticipationRepository.countByMember_Id(memberId);

        return ParticipationProjectResponse.from(projectIds, count);
    }
}
