package com.example.lionking.domain.member.controller;

import com.example.lionking.domain.member.dto.MemberRequest;
import com.example.lionking.domain.member.dto.MemberResponse;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.service.MemberService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "유저 프로필 정보 관련 API")
public class MemberController {

    private final MemberService memberService;

    /**
     * [READ]
     */
    @GetMapping
    @Operation(summary = "전체 멤버 조회")
    public ApiResponse<List<MemberResponse>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        List<MemberResponse> responseList = members.stream()
                .map(MemberResponse::from)
                .toList();
        return ApiResponse.success(responseList, "전체 MEMBER 조회 성공");
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "단일 멤버 조회")
    public ApiResponse<MemberResponse> getMemberById(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return ApiResponse.success(MemberResponse.from(member), "단일 MEMBER 조회 성공");
    }

    @PutMapping("/{memberId}")
    @Operation(summary = "멤버 프로필 수정")
    public ApiResponse<MemberResponse> updateMemberProfile(
            @PathVariable Long memberId,
            @RequestBody MemberRequest request) {

        Member updated = memberService.updateProfile(memberId, request);
        return ApiResponse.success(MemberResponse.from(updated), "멤버 프로필 수정 성공");
    }

}