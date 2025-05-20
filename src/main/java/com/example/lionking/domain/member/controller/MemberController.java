package com.example.lionking.domain.member.controller;

import com.example.lionking.domain.member.dto.SignUpRequest;
import com.example.lionking.domain.member.dto.SignUpResponse;
import com.example.lionking.domain.member.service.MemberService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원가입, 로그인 등의 회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody SignUpRequest request) {
        // TODO : 예외처리
        SignUpResponse response = memberService.signUp(request);
        return ResponseEntity.ok(ApiResponse.success(response, "회원가입에 성공했습니다."));
    }
}