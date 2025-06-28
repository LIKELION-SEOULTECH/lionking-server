package com.example.lionking.domain.blog.service;

import com.example.lionking.domain.blog.dto.BlogImageRequest;
import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.blog.entitiy.BlogImage;
import com.example.lionking.domain.blog.repository.BlogRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BlogResponse postBlog(BlogRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        Blog blog = Blog.builder()
                .blogType(request.blogType())
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();

        for (BlogImageRequest img : request.images()) {
            BlogImage blogImage = BlogImage.builder()
                    .s3Key(img.s3Key())
                    .imageType(img.imageType())
                    .blog(blog)
                    .build();

            blog.addBlogImage(blogImage);
        }
        return BlogResponse.from(blogRepository.save(blog));
    }

    @Transactional(readOnly = true)
    public BlogResponse getBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        return BlogResponse.from(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponse> getBlogsByAuthorId(Long authorId) {
        List<Blog> blogs = blogRepository.findAllByAuthorId(authorId);
        return blogs.stream()
                .map(BlogResponse::from)
                .toList();
    }

    @Transactional
    public BlogResponse updateBlog(Long blogId, BlogRequest request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // 기존 이미지 제거
        for (BlogImage image : new ArrayList<>(blog.getBlogImages())) {
            blog.removeBlogImage(image);
        }

        // 새로운 이미지 업데이트
        for (BlogImageRequest img : request.images()) {
            BlogImage blogImage = BlogImage.builder()
                    .s3Key(img.s3Key())
                    .imageType(img.imageType())
                    .build();
            blog.addBlogImage(blogImage);
        }

        blog.update(request.title(), request.content());
        return BlogResponse.from(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));
        blogRepository.delete(blog);
    }
}
