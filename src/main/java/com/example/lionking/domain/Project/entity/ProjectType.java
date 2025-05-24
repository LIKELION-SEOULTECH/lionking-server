package com.example.lionking.domain.Project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectType {
    IDEATHON("아이디어톤"),
    CENTRAL_HACKATHON("중앙해커톤"),
    UNION_HACKATHON("연합해커톤"),
    LONG_TERM("장기프로젝트"),
    ETC("기타");
    private final String displayName;
}
