package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.ProjectParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectParticipationRepository extends JpaRepository<ProjectParticipation, Long> {
    List<ProjectParticipation> findByMember_Id(Long memberId);
}
