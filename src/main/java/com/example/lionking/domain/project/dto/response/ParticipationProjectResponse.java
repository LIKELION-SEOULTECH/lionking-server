package com.example.lionking.domain.project.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
public class ParticipationProjectResponse {
    private List<Long> projectIds;
    private int count;

    @Builder
    public ParticipationProjectResponse(List<Long> projectIds, int count) {
        this.projectIds = projectIds;
        this.count = count;
    }

    public static ParticipationProjectResponse from(List<Long> projectIds, int count) {
       return ParticipationProjectResponse.builder()
                .projectIds(projectIds)
                .count(count)
                .build();
    }
}
