package com.example.lionking.global.s3.dto;

public record GetPresignedUrlResponse(
        String url,
        String filePath
) {
    public static GetPresignedUrlResponse of(String url, String filePath) {
        return new GetPresignedUrlResponse(url,filePath);
    }
}
