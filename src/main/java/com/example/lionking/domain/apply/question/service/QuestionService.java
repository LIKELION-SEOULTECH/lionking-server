package com.example.lionking.domain.apply.question.service;

import com.example.lionking.domain.apply.question.dto.QuestionRequest;
import com.example.lionking.domain.apply.question.dto.QuestionResponse;
import com.example.lionking.domain.apply.question.entitiy.Question;
import com.example.lionking.domain.apply.question.repository.QuestionRepository;
import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import com.example.lionking.domain.recruit.term.repository.RecruitmentTermRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final RecruitmentTermRepository recruitmentTermRepository;

    @Transactional
    public List<QuestionResponse> register(QuestionRequest request) {
        RecruitmentTerm term = recruitmentTermRepository.findById(request.recruitmentTermId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "해당 기수 정보가 존재하지 않습니다."));

        questionRepository.deleteAllByRecruitmentTerm(term);

        List<Question> newQuestions = request.questions().stream()
                .map(q -> Question.builder()
                        .recruitmentTerm(term)
                        .content(q.content())
                        .sequence(q.sequence())
                        .build())
                .toList();

        return questionRepository.saveAll(newQuestions).stream()
                .map(QuestionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions(Long recruitmentTermId) {
        return questionRepository.findAllByRecruitmentTermIdOrderBySequenceAsc(recruitmentTermId).stream()
                .map(QuestionResponse::from)
                .toList();
    }

}
