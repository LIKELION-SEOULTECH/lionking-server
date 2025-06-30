package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.ProjectRetrospection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRetrospectionRepository extends JpaRepository<ProjectRetrospection, Long> {
    // 멤버 id와 프로젝트 id로 회고 조회
    Optional<ProjectRetrospection> findByMemberIdAndProjectId(Long memberId, Long projectId);
}
