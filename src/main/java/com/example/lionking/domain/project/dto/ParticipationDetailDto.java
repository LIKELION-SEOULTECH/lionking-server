package com.example.lionking.domain.project.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipationDetailDto {
    private Long memberId;
    private String username;
    private String profileImage;
    private String position;
    private String role;
    private String retrospection;
}
