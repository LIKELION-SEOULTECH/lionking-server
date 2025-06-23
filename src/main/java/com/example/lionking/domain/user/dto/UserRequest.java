package com.example.lionking.domain.user.dto;

import com.example.lionking.domain.user.entity.User;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.entity.Position;
import com.example.lionking.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User 요청")
public record UserRequest(

        // TODO : 회원 고유가입번호 추가

        @Schema(description = "로그인 아이디", example = "lionking123")
        String loginId,
        @Schema(description = "비밀번호", example = "securePassword123!")
        String password,
        @Schema(description = "사용자 이름", example = "김멋사")
        String username,
        @Schema(description = "이메일", example = "lion@seoultech.ac.kr")
        String email,
        @Schema(description = "직무파트", example = "BACKEND")
        String position
) {

    public User toUser(String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .email(email)
                .build();
    }

    public Member toMember(User user) {
        return Member.builder()
                .username(username)
                .position(Position.valueOf(position.toUpperCase()))
                .role(Role.USER) // 회원가입 시 디폹트 권한 = 아기사자
                .user(user)
                .build();
    }

}