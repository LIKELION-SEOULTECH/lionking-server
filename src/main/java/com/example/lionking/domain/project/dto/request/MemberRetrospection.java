package com.example.lionking.domain.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "멤버 회고 요청 dto")
public class MemberRetrospection {
    private Long memberId;
    private String retrospection;
}
