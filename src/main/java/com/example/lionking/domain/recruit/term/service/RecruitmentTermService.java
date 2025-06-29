package com.example.lionking.domain.recruit.term.service;

import com.example.lionking.domain.recruit.term.dto.RecruitmentTermRequest;
import com.example.lionking.domain.recruit.term.dto.RecruitmentTermResponse;
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
public class RecruitmentTermService {

    private final RecruitmentTermRepository recruitmentTermRepository;

    @Transactional
    public RecruitmentTermResponse create(RecruitmentTermRequest request) {
        RecruitmentTerm term = RecruitmentTerm.builder()
                .generation(request.generation())
                .target(request.target())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
        return RecruitmentTermResponse.from(recruitmentTermRepository.save(term));
    }

    @Transactional(readOnly = true)
    public List<RecruitmentTermResponse> findAll() {
        return recruitmentTermRepository.findAll().stream()
                .map(RecruitmentTermResponse::from)
                .toList();
    }

    @Transactional
    public RecruitmentTermResponse updateEndDate(Long id, RecruitmentTermRequest request) {
        RecruitmentTerm term = recruitmentTermRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "해당 ID의 모집 기수를 찾을 수 없습니다."));
        term.updateEndDate(request.endDate());
        return RecruitmentTermResponse.from(term);
    }

    @Transactional
    public void delete(Long id) {
        recruitmentTermRepository.deleteById(id);
    }
}
