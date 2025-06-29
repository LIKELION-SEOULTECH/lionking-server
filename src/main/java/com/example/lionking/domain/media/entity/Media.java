package com.example.lionking.domain.media.entity;

import com.example.lionking.domain.blog.entitiy.Blog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String s3Key;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Enumerated(EnumType.STRING)
    private MediaOwner mediaOwner;

    private Long ownerId; // BlogId 또는 NoticeId

    @Builder
    public Media(String s3Key, MediaType mediaType, MediaOwner mediaOwner, Long ownerId) {
        this.s3Key = s3Key;
        this.mediaType = mediaType;
        this.mediaOwner = mediaOwner;
        this.ownerId = ownerId;
    }

}