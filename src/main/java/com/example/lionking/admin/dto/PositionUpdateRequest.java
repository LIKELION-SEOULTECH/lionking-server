package com.example.lionking.admin.dto;

import com.example.lionking.domain.member.entity.Position;
import io.swagger.v3.oas.annotations.media.Schema;

public record PositionUpdateRequest(
        @Schema(description = "변경할 파트", example = "DESIGN")
        Position position
) {
}
