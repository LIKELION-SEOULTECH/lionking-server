package com.example.lionking.domain.media.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaOwner {
    BLOG("블로그"),
    NOTICE("공지사항")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
