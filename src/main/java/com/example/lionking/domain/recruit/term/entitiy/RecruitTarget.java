package com.example.lionking.domain.recruit.term.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitTarget {
    MEMBER("아기사자"),
    MANAGER("운영진")
    ;

    private final String description;

}