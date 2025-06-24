package com.example.lionking.domain.media.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaOwner {
    BLOG("블로그"),
    NOTICE("공지사항"),
    ACTIVITY("활동기록"),
    PROJECT("프로젝트")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
