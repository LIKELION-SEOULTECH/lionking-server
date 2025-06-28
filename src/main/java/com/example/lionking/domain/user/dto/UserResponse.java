package com.example.lionking.domain.user.dto;

import com.example.lionking.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User 응답")
public record UserResponse(
        @Schema(description = "User ID", example = "1")
        Long userId,
        @Schema(description = "Member ID", example = "1")
        Long memberId,
        @Schema(description = "로그인 아이디", example = "lionking123")
        String loginId,
        @Schema(description = "사용자 이름", example = "김멋사")
        String username,
        @Schema(description = "이메일", example = "lion@seoultech.ac.kr")
        String email,
        @Schema(description = "직무파트", example = "BACKEND")
        String position,
        @Schema(description = "권한", example = "USER")
        String role
) {
    public static UserResponse from(Member member) {
        return new UserResponse(
                member.getUser().getId(),
                member.getId(),
                member.getUser().getLoginId(),
                member.getUsername(),
                member.getUser().getEmail(),
                member.getPosition().name(),
                member.getRole().name()
        );
    }
}