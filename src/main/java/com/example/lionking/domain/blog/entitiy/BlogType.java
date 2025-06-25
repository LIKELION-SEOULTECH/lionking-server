package com.example.lionking.domain.blog.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlogType {
    SESSION("세션"),
    ARTICLE("아티클")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}