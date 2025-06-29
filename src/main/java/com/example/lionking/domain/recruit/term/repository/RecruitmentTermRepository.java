package com.example.lionking.domain.recruit.term.repository;

import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentTermRepository extends JpaRepository<RecruitmentTerm, Long> {
    boolean existsByGenerationAndTarget(int generation, com.example.lionking.domain.recruit.term.entitiy.RecruitTarget target);
}