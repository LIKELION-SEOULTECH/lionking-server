package com.example.lionking.admin.dto;

import com.example.lionking.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record RoleUpdateRequest(
        @Schema(description = "변경할 권한", example = "MANAGER")
        Role role
) {
}
