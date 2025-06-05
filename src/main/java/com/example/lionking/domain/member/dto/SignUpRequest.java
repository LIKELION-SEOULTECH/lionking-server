package com.example.lionking.domain.member.dto;

import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.entity.Position;
import com.example.lionking.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        @Schema(description = "사용자 이름", example = "권현욱")
        String username,
        @Schema(description = "직무파트", example = "BACKEND")
        String position,
        @Schema(description = "권한", example = "USER")
        String role
) {
    public Member toEntity() {
        return Member.builder()
                .username(username)
                .position(Position.valueOf(position.toUpperCase()))
                .role(Role.valueOf(role.toUpperCase()))
                .build();
    }
}