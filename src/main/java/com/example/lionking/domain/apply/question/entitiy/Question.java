package com.example.lionking.domain.apply.question.entitiy;

import com.example.lionking.domain.recruit.term.entitiy.RecruitmentTerm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_term_id", nullable = false)
    private RecruitmentTerm recruitmentTerm;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer sequence; // 질문 순서

}