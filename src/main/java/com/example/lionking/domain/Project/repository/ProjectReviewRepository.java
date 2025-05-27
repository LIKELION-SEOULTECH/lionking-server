package com.example.lionking.domain.Project.repository;

import com.example.lionking.domain.Project.entity.ProjectParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectReviewRepository extends JpaRepository<ProjectParticipation, Long> {
}
