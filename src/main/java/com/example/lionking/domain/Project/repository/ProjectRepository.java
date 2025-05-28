package com.example.lionking.domain.Project.repository;

import com.example.lionking.domain.Project.entity.Project;
import com.example.lionking.domain.Project.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByGenerationAndProjectType(String generation, ProjectType projectType);
}
