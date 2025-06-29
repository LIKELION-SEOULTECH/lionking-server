package com.example.lionking.domain.apply.question.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "질문 목록 등록/갱신 요청")
public record QuestionRequest(
        @Schema(description = "모집 기수 ID", example = "1")
        Long recruitmentTermId,
        @Schema(
                description = "질문 목록",
                example = """
                        [
                          {
                            "content": "자기소개를 해주세요.",
                            "sequence": 1
                          },
                          {
                            "content": "멋쟁이 사자처럼 활동에 지원한 이유는 무엇인가요?",
                            "sequence": 2
                          },
                          {
                            "content": "협업 프로젝트 경험이 있다면 소개해주세요.",
                            "sequence": 3
                          },
                          {
                            "content": "사용해본 기술 스택과 자신 있는 기술을 알려주세요.",
                            "sequence": 4
                          },
                          {
                            "content": "기타 하고 싶은 말이 있다면 자유롭게 작성해주세요.",
                            "sequence": 5
                          }
                        ]"""
        )
        List<QuestionItem> questions
) {}