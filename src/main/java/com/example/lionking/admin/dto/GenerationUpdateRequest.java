package com.example.lionking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GenerationUpdateRequest(
        @Schema(description = "변경할 기수", example = "13")
        int generation
) {
}
