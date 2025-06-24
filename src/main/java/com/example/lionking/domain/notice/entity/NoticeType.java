package com.example.lionking.domain.notice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {
    GENERAL("일반 공지"),
    IMPORTANT("중요 공지"),
    EMERGENCY("긴급 공지")
    ;

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
