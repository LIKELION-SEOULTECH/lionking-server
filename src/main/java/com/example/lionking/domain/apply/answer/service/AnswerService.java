package com.example.lionking.domain.apply.answer.service;

import com.example.lionking.domain.apply.answer.dto.AnswerItem;
import com.example.lionking.domain.apply.answer.dto.AnswerRequest;
import com.example.lionking.domain.apply.answer.dto.AnswerResponse;
import com.example.lionking.domain.apply.answer.entity.Answer;
import com.example.lionking.domain.apply.answer.repository.AnswerRepository;
import com.example.lionking.domain.apply.question.entitiy.Question;
import com.example.lionking.domain.apply.question.repository.QuestionRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import com.example.lionking.domain.recruit.term.repository.RecruitmentTermRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final MemberRepository memberRepository;
    private final RecruitmentTermRepository recruitmentTermRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public List<AnswerResponse> getAnswersByMemberAndTerm(Long memberId, Long termId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 멤버입니다."));

        RecruitmentTerm term = recruitmentTermRepository.findById(termId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 기수입니다."));

        List<Question> questions = questionRepository.findAllByRecruitmentTermOrderBySequenceAsc(term);

        return questions.stream()
                .map(q -> {
                    Answer answer = answerRepository.findByQuestionAndApplicant(q, member)
                            .orElse(null);
                    return new AnswerResponse(
                            q.getId(),
                            q.getContent(),
                            q.getSequence(),
                            answer != null ? answer.getContent() : null
                    );
                })
                .toList();
    }

    @Transactional
    public void submitAnswers(Long memberId, AnswerRequest request) {

        // TODO : 지원 마감일 확인 로직 추가

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 멤버"));

        for (AnswerItem item : request.answers()) {
            Question question = questionRepository.findById(item.questionId())
                    .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 질문"));

            Optional<Answer> existing = answerRepository.findByQuestionAndApplicant(question, member);
            if (existing.isPresent()) {
                existing.get().updateContent(item.content());
            } else {
                Answer answer = Answer.builder()
                        .question(question)
                        .applicant(member)
                        .content(item.content())
                        .build();
                answerRepository.save(answer);
            }
        }
    }

}