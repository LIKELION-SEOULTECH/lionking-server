package com.example.lionking.domain.project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {
    THUMBNAIL("썸네일"),
    LANDING("랜딩");
    private final String title;
}
