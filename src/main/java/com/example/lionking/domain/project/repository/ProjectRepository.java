package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.Project;
import com.example.lionking.domain.project.entity.ProjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByProjectTypeAndGeneration(ProjectType projectType, Integer generation, Pageable pageable);

    Page<Project> findAllByProjectType(ProjectType projectType, Pageable pageable);

    Page<Project> findAllByGeneration(Integer generation, Pageable pageable);
}

/*    @Query("select p from Project p left join fetch " )
    List<Project> findAllByGenerationAndProjectType(
            @Param("generation") String generation,
            @Param("projectType") ProjectType projectType
    );*/
