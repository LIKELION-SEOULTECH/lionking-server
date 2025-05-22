package com.example.lionking.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "유저"),
    ADMIN("ROLE_ADMIN", "관리자"),
    ;

    private final String authority;
    private final String description;
}