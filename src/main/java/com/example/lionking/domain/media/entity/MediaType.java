package com.example.lionking.domain.media.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaType {
    IMAGE("이미지"),
    VIDEO("비디오")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
