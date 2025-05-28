package com.example.lionking.domain.Project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Key;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public ProjectImage(String s3Key, ImageType imageType) {
        this.s3Key = s3Key;
        this.imageType = imageType;
    }

    public static ProjectImage of(String s3Key, ImageType imageType) {
        return ProjectImage.builder()
                .s3Key(s3Key)
                .imageType(imageType)
                .build();
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
