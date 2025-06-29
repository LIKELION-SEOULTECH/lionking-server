package com.example.lionking.domain.apply.answer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "지원서 제출 요청")
public record AnswerRequest(
        @Schema(description = "모집 기수 ID", example = "1")
        Long termId,
        @Schema(description = "답변 목록", example = """
            [
              {
                "questionId": 1,
                "content": "저는 백엔드를 주로 다루고 있는 개발자입니다."
              },
              {
                "questionId": 2,
                "content": "멋사 활동을 통해 실무에 가까운 협업을 경험하고 싶습니다."
              },
              {
                "questionId": 3,
                "content": "교내 해커톤에서 3명의 팀원과 함께 웹 프로젝트를 진행한 경험이 있습니다."
              },
              {
                "questionId": 4,
                "content": "JAVA, SPRING, MYSQL 등을 사용해보았으며, 특히 RESTful API 설계에 자신이 있습니다."
              },
              {
                "questionId": 5,
                "content": "좋은 사람들과 함께 성장하고 싶습니다!"
              }
            ]
        """)
        List<AnswerItem> answers
) {}
