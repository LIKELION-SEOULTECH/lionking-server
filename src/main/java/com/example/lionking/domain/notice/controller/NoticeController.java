package com.example.lionking.domain.notice.controller;

import com.example.lionking.domain.notice.dto.NoticeRequest;
import com.example.lionking.domain.notice.dto.NoticeResponse;
import com.example.lionking.domain.notice.service.NoticeService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
@Tag(name = "Notice API", description = "공지사항 등록 및 관리")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    @Operation(summary = "모든 공지 목록 조회", description = "등록된 모든 공지사항을 조회합니다.")
    public ApiResponse<List<NoticeResponse>> getAllNotices() {
        List<NoticeResponse> responses = noticeService.getAllNotices();
        return ApiResponse.success(responses, "공지사항 목록 조회 성공");
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "공지 상세 조회", description = "특정 공지사항을 조회합니다.")
    public ApiResponse<NoticeResponse> getNotice(@PathVariable Long noticeId) {
        NoticeResponse response = noticeService.getNotice(noticeId);
        return ApiResponse.success(response, "공지사항 조회 성공");
    }

}