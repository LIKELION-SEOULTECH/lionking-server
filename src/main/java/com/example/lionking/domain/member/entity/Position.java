package com.example.lionking.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position {
    PLAN("POSITION_PLAN", "기획"),
    DESIGN("POSITION_DESIGN", "디자인"),
    FRONTEND("POSITION_FRONTEND", "프론트"),
    BACKEND("POSITION_BACKEND", "백엔드"),
    AI("POSITION_AI", "AI")
    ;

    private final String position;
    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
