package com.example.lionking.admin.service;

import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.entity.Role;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMemberRole(Long memberId, Role newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 멤버입니다."));
        member.updateRole(newRole);
    }
}
