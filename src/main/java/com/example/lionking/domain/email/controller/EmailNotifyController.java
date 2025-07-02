package com.example.lionking.domain.email.controller;

import com.example.lionking.domain.email.dto.request.EmailRequest;
import com.example.lionking.domain.email.dto.response.EmailResponse;
import com.example.lionking.domain.email.service.BulkEmailService;
import com.example.lionking.domain.email.service.EmailNotifyService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Tag(name = "이메일 알림 공지 API")
public class EmailNotifyController {

    private final EmailNotifyService emailNotifyService;
    private final BulkEmailService bulkEmailService;

    @Operation(summary = "이메일 등록 API", description = "이메일 등록 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> registerEmail(@RequestBody @Valid EmailRequest req) {
        return ApiResponse.success("이메일 등록 성공");
    }

/*    // 통계 조회 API
    @GetMapping("/stats")
    public ResponseEntity<NotifyStatsResponse> getStats() {
        NotifyStatsResponse stats = notifyEmailService.getStats();
        return ResponseEntity.ok(stats);
    }

    // 수동 알림 발송 API (테스트용)
    @PostMapping("/send-now")
    public ResponseEntity<String> sendNotificationNow() {
        bulkEmailService.sendBulkNotifications();
        return ResponseEntity.ok("알림 발송이 시작되었습니다.");
    }*/
}
