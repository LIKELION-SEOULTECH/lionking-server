package com.example.lionking.admin.controller;

import com.example.lionking.domain.notice.dto.NoticeRequest;
import com.example.lionking.domain.notice.dto.NoticeResponse;
import com.example.lionking.domain.notice.service.NoticeService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/notice")
@RequiredArgsConstructor
@Tag(name = "Admin Notice API", description = "관리자 : 공지사항 API")
public class AdminNoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 작성", description = "공지사항 작성 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @PostMapping("/{authorId}")
    public ApiResponse<NoticeResponse> postNotice(
            @PathVariable Long authorId,
            @Valid @RequestBody NoticeRequest request
    ) {
        NoticeResponse response = noticeService.postNotice(request, authorId);
        return ApiResponse.success(response, "공지사항 작성 성공");
    }

    @Operation(summary = "공지사항 수정", description = "공지사항 수정 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @PatchMapping("/{noticeId}")
    public ApiResponse<NoticeResponse> updateNotice(
            @PathVariable Long noticeId,
            @Valid @RequestBody NoticeRequest request
    ) {
        NoticeResponse response = noticeService.updateNotice(noticeId, request);
        return ApiResponse.success(response, "공지사항 수정 성공");
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항 삭제 : 운영진 이상만 호출 가능")
    @PreAuthorize("hasRole('MANAGER') or hasRole('REPRESENTATIVE')")
    @DeleteMapping("/{noticeId}")
    public ApiResponse<Object> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ApiResponse.success("공지사항 삭제 성공");
    }

}