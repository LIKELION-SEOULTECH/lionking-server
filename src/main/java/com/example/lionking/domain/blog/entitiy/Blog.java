package com.example.lionking.domain.blog.entitiy;

import com.example.lionking.domain.media.entity.Media;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BlogType blogType;

    private String thumbnailImage;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public Blog(BlogType blogType, String thumbnailImage, String title, String content, String summary, Member author) {
        this.blogType = blogType;
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.author = author;
    }

    public void update(String thumbnailImage, String title, String content, String summary) {
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.content = content;
        this.summary = summary;
    }
}