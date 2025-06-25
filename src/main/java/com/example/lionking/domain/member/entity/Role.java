package com.example.lionking.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    REPRESENTATIVE("ROLE_REPRESENTATIVE", "대표"),
    MANAGER("ROLE_MANAGER", "운영진"),
    USER("ROLE_USER", "아기사자"),
    PREVIOUS("ROLE_PREVIOUS", "지난기수"),
    ;

    private final String authority;
    private final String description;

    @Override
    public String toString() {
        return description;
    }
}