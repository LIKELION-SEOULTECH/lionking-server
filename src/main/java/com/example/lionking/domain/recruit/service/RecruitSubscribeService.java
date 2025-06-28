package com.example.lionking.domain.recruit.service;

import com.example.lionking.domain.recruit.dto.RecruitSubscribeResponse;
import com.example.lionking.domain.recruit.entity.RecruitSubscribe;
import com.example.lionking.domain.recruit.repository.RecruitSubscribeRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitSubscribeService {

    private final RecruitSubscribeRepository recruitSubscribeRepository;

    public void subscribe(String email) {
        if (recruitSubscribeRepository.existsByEmail(email)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST, "이미 신청된 이메일입니다.");
        }
        recruitSubscribeRepository.save(RecruitSubscribe.builder()
                .email(email)
                .isNotified(false)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Transactional(readOnly = true)
    public List<RecruitSubscribeResponse> getAllSubscribers() {
        return recruitSubscribeRepository.findAll().stream()
                .map(RecruitSubscribeResponse::from)
                .toList();
    }
}