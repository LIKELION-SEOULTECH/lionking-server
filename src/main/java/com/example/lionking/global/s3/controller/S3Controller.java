package com.example.lionking.global.s3.controller;

import com.example.lionking.global.response.ApiResponse;
import com.example.lionking.global.s3.dto.GetPresignedUrlRequest;
import com.example.lionking.global.s3.dto.GetPresignedUrlResponse;
import com.example.lionking.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
@Tag(name = "S3", description = "S3 파일 업로드 관련 API")
public class S3Controller {
    private final S3Service s3Service;

    @Operation(summary = "업로드용 Presigned URL 생성", description = "파일 업로드를 위한 Presigned URL을 생성합니다.")
    @PutMapping
    public ApiResponse<GetPresignedUrlResponse> getPresignedUrlForUpload(
            @RequestBody @Valid GetPresignedUrlRequest request) {

        GetPresignedUrlResponse response = s3Service.getPresignedUrlForPut(request);
        return ApiResponse.success(response);
    }

}
