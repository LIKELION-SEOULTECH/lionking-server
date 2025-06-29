package com.example.lionking.domain.recruit.subscribe.repository;

import com.example.lionking.domain.recruit.subscribe.entity.RecruitSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitSubscribeRepository extends JpaRepository<RecruitSubscribe, Long> {
    boolean existsByEmail(String email);
    List<RecruitSubscribe> findAllByIsNotifiedFalse();
}