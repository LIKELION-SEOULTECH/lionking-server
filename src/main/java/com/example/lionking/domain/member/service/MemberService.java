package com.example.lionking.domain.member.service;

import com.example.lionking.domain.member.dto.MemberRequest;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "해당 멤버를 찾을 수 없습니다."));
    }

    @Transactional
    public Member updateProfile(Long memberId, MemberRequest request) {
        Member member = getMemberById(memberId);
        member.updateProfile(
                request.username(),
                request.department(),
                request.descriptionTag(),
                request.description(),
                request.techStack(),
                request.portfolioUrls(),
                request.position()
        );
        return member;
    }
}
