package com.example.lionking.domain.apply.question.repository;

import com.example.lionking.domain.apply.question.entitiy.Question;
import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteAllByRecruitmentTerm(RecruitmentTerm recruitmentTerm);
    List<Question> findAllByRecruitmentTermIdOrderBySequenceAsc(Long recruitmentTermId);

    List<Question> findAllByRecruitmentTermOrderBySequenceAsc(RecruitmentTerm term);

}