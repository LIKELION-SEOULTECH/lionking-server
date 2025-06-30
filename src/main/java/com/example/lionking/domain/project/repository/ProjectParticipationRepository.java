package com.example.lionking.domain.project.repository;

import com.example.lionking.domain.project.entity.ProjectParticipation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectParticipationRepository extends JpaRepository<ProjectParticipation, Long> {
    List<ProjectParticipation> findByMember_Id(Long memberId);

    // 멤버가 참여한 프로젝트의 ID 리스트
    @Query("select p.project.id from ProjectParticipation p where p.member.id = :memberId")
    List<Long> findProjectIdsByMemberId(@Param("memberId") Long memberId);

    // 멤버가 참여한 프로젝트의 개수
    int countByMember_Id(Long memberId);
}
