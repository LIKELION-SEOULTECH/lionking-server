package com.example.lionking.domain.blog.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogMediaType {
    IMAGE("이미지"),
    VIDEO("비디오")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
