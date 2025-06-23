package com.example.lionking.domain.blog.entitiy;

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

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogMedia> blogMedia = new ArrayList<>();

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder
    public Blog(BlogType blogType, String thumbnailImage, String title, String content, Member author) {
        this.blogType = blogType;
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String thumbnailImage, String title, String content) {
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.content = content;
    }

    /**
     * Convenience Method
     */
    public void addBlogImage(BlogMedia blogMedia) {
        if (blogMedia != null) {
            this.blogMedia.add(blogMedia);
            blogMedia.setBlog(this);
        }
    }
    public void removeBlogImage(BlogMedia blogMedia) {
        if (blogMedia == null) return;

        if (this.blogMedia.remove(blogMedia)) {
            blogMedia.removeBlog();
        }
    }
}