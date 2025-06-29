package com.example.lionking.domain.recruit.term.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RecruitmentTerm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int generation; // 기수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitTarget target; // 모집 대상

    private LocalDate startDate;
    private LocalDate endDate;

    public void updateEndDate(LocalDate newEndDate) {
        this.endDate = newEndDate;
    }

}