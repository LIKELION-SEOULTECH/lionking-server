package com.example.lionking.domain.member.dto;

import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.entity.Position;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Member 요청")
public record MemberRequest(
        @Schema(description = "사용자 이름", example = "김멋사")
        String username,
        @Schema(description = "전공", example = "컴퓨터공학과")
        String department,
        @Schema(description = "직무파트", example = "BACKEND")
        String position,
        @Schema(description = "소개태그", example = "#긍정적인, #열정적인, #창의적인")
        String descriptionTag,
        @Schema(description = "자기소개", example = "13기 멋사 백엔드 파트 아기사자 김멋사입니다.")
        String description,
        @Schema(description = "기술스택", example = "JAVA, MYSQL, REDIS, DOCKER, AWS")
        String techStack,
        @Schema(description = "포트폴리오", example = "Github:https://github.com/Jeongh00, Github:https://github.com/dohyeoplim, ")
        String portfolioUrls
) {

    public Member toMember() {
        return Member.builder()
                .username(username)
                .department(department)
                .position(Position.valueOf(position.toUpperCase()))
                .descriptionTag(descriptionTag)
                .description(description)
                .techStack(techStack)
                .portfolioUrls(portfolioUrls)
                .build();
    }

}