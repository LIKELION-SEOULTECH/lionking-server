package com.example.lionking.domain.user.controller;

import com.example.lionking.domain.user.entity.User;
import com.example.lionking.domain.user.service.UserService;
import com.example.lionking.domain.user.dto.UserRequest;
import com.example.lionking.domain.user.dto.UserResponse;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "User 로그인")
public class UserController {

    private final UserService userService;

    /**
     * [CREATE]
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.")
    public ApiResponse<UserResponse> signup(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.signUp(request);
        return ApiResponse.success(response, "회원가입 성공");
    }

    /**
     * [READ]
     */
    @GetMapping
    @Operation(summary = "전체 USER 조회")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers(), "전체 USER 조회 성공");
    }

}
