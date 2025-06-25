/*
package com.example.lionking.domain.auth.controller;

import com.example.lionking.domain.auth.service.AuthService;
import com.example.lionking.domain.auth.dto.LoginRequest;
import com.example.lionking.domain.auth.dto.LoginResponse;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 후 Token 발급")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request);

        // JWT를 응답 헤더에 추가
        response.setHeader("Authorization", "Bearer " + loginResponse.accessToken());
        response.setHeader("X-Refresh-Token", loginResponse.refreshToken());

        return ApiResponse.success(loginResponse, "로그인 성공");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "RefreshToken 제거")
    public ApiResponse<Object> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return ApiResponse.success("로그아웃 성공");
    }

    @PostMapping("/reissue")
    @Operation(
            summary = "Access + Refresh Token 재발급",
            description = "헤더에 들어있는 Refresh Token을 기반으로 Access Token 및 Refresh Token 재발급"
    )
    public ApiResponse<Map<String, String>> reissue(
            @Parameter(description = "기존 Refresh Token", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NTAyMzA3MzQsImV4cCI6MTc1MDMxNzEzNH0.nOwWdGMlJXjg8K27tCSB6y5Sf0Q-JRnpliwMrwIbKzg")
            @RequestHeader("X-Refresh-Token")
            String oldRefreshToken,
            HttpServletResponse response
    ) {
        Map<String, String> newTokens = authService.reissueTokens(oldRefreshToken);

        // 헤더에 추가
        response.setHeader("Authorization", "Bearer " + newTokens.get("accessToken"));
        response.setHeader("X-Refresh-Token", newTokens.get("refreshToken"));

        return ApiResponse.success(newTokens, "Access + Refresh Token 재발급 성공");
    }

}*/
