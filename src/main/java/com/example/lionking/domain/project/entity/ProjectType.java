package com.example.lionking.domain.project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum ProjectType {
    IDEATHON("아이디어톤"),
    CENTRAL_HACKATHON("중앙해커톤"),
    UNION_HACKATHON("연합해커톤"),
    LONG_TERM("장기프로젝트"),
    ETC("기타");
    private final String displayName;
}

