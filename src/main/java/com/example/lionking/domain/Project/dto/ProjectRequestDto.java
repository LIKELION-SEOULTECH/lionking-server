package com.example.lionking.domain.Project.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectRequestDto(
    @NotNull
    String title,
    @NotNull
    String projectType,
    @NotNull
    String projectDescription,
    String videoLink,
    @NotNull
    List<Long> memberIds,
    @NotNull
    String thumbnailImageKey,
    @NotNull
    List<String> landingImagesKeys,
    @NotNull
    String review
) {}
