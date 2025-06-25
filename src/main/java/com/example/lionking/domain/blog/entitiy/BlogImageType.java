package com.example.lionking.domain.blog.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogImageType {
    THUMBNAIL("썸네일"),
    CONTENT("본문")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
