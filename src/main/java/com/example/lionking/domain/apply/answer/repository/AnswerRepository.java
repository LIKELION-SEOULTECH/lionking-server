package com.example.lionking.domain.apply.answer.repository;

import com.example.lionking.domain.apply.answer.entity.Answer;
import com.example.lionking.domain.apply.question.entitiy.Question;
import com.example.lionking.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByQuestionAndApplicant(Question question, Member member);

}
