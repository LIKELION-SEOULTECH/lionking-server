package com.example.lionking.admin.controller;

import com.example.lionking.admin.dto.GenerationUpdateRequest;
import com.example.lionking.admin.dto.PositionUpdateRequest;
import com.example.lionking.admin.dto.RoleUpdateRequest;
import com.example.lionking.admin.service.AdminService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "관리자 기능 API")
public class AdminController {

    private final AdminService adminService;

    /**
     * 대표 권한 이상만 허용
     */

    @Operation(
            summary = "멤버 권한 변경",
            description = "멤버의 권한을 변경합니다. 대표 권한 이상만 호출 가능합니다."
    )
    @PreAuthorize("hasRole('REPRESENTATIVE')")
    @PatchMapping("/role/{memberId}")
    public ApiResponse<Object> updateRole(
            @Parameter(description = "멤버 ID", example = "2")
            @PathVariable Long memberId,
            @RequestBody RoleUpdateRequest request
    ) {
        adminService.updateMemberRole(memberId, request.role());
        return ApiResponse.success("멤버 권한 업데이트 성공");
    }

    @Operation(
            summary = "멤버 파트 변경",
            description = "멤버의 파트를 변경합니다. 대표 권한 이상만 호출 가능합니다."
    )
    @PreAuthorize("hasRole('REPRESENTATIVE')")
    @PatchMapping("/position/{memberId}")
    public ApiResponse<Object> updatePosition(
            @Parameter(description = "멤버 ID", example = "2")
            @PathVariable Long memberId,
            @RequestBody PositionUpdateRequest request
    ) {
        adminService.updateMemberPosition(memberId, request.position());
        return ApiResponse.success("멤버 파트 업데이트 성공");
    }

    @Operation(
            summary = "멤버 기수 변경",
            description = "멤버의 기수를 변경합니다. 대표 권한 이상만 호출 가능합니다."
    )
    @PreAuthorize("hasRole('REPRESENTATIVE')")
    @PatchMapping("/generation/{memberId}")
    public ApiResponse<Object> updateGeneration(
            @Parameter(description = "멤버 ID", example = "2")
            @PathVariable Long memberId,
            @RequestBody GenerationUpdateRequest request
    ) {
        adminService.updateMemberGeneration(memberId, request.generation());
        return ApiResponse.success("멤버 기수 업데이트 성공");
    }

    /**
     * 운영진 권한 이상만 허용
     */

}