package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.ProjectParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectReviewRepository extends JpaRepository<ProjectParticipation, Long> {
}
