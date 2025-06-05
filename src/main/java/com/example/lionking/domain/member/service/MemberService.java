package com.example.lionking.domain.member.service;

import com.example.lionking.domain.member.dto.SignUpRequest;
import com.example.lionking.domain.member.dto.SignUpResponse;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        Member saved = memberRepository.save(request.toEntity());
        return SignUpResponse.from(saved);
    }
}
