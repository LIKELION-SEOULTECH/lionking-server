package com.example.lionking.domain.email.repository;

import com.example.lionking.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailNotifyRepository extends JpaRepository<Email, Long> {
    // 아직 알림을 받지 않은 이메일 목록 조회
    List<Email> findByNotifiedFalse();
    // 이메일 중복 체크
    boolean existsByEmail(String email);
    // 알림 발송 완료된 이메일 개수
    long countByNotifiedTrue();
}
