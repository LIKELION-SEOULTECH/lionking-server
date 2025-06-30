package com.example.lionking.domain.auth.dto;

import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.entity.Position;
import com.example.lionking.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserResponse {
    private Long id;
    private String name;
    private String department;
    private Position position;
    private Role role;
    private int generation;

    @Builder
    public LoginUserResponse(Long id, String name, String department, Position position, Role role, int generation) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.role = role;
        this.generation = generation;
    }

    public static LoginUserResponse from(Member member) {
       return LoginUserResponse.builder()
                .id(member.getId())
                .name(member.getUsername())
                .department(member.getDepartment())
                .position(member.getPosition())
                .role(member.getRole())
                .generation(member.getGeneration())
                .build();
    }
}
