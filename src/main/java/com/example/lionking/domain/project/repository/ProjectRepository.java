package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.Project;
import com.example.lionking.domain.project.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
/*    @Query("select p from Project p left join fetch " )
    List<Project> findAllByGenerationAndProjectType(
            @Param("generation") String generation,
            @Param("projectType") ProjectType projectType
    );*/
}
