package com.example.lionking.domain.member.dto;

import com.example.lionking.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")
public record SignUpResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,
        @Schema(description = "사용자 이름", example = "권현욱")
        String username,
        @Schema(description = "직무파트", example = "BACKEND")
        String position,
        @Schema(description = "권한", example = "USER")
        String role
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getUsername(),
                member.getPosition().name(),
                member.getRole().name()
        );
    }
}