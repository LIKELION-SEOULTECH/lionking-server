package com.example.lionking.domain.blog.service;

import com.example.lionking.domain.media.dto.MediaRequest;
import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.media.dto.MediaResponse;
import com.example.lionking.domain.blog.repository.BlogRepository;
import com.example.lionking.domain.media.entity.MediaOwner;
import com.example.lionking.domain.media.service.MediaService;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;
    private final MediaService mediaService;

    @Transactional
    public BlogResponse postBlog(BlogRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 유저입니다."));

        Blog blog = Blog.builder()
                .blogType(request.blogType())
                .thumbnailImage(request.thumbnailImage())
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
        blogRepository.save(blog);

        // 미디어 저장
        List<MediaRequest> mediaRequests = request.contentMedia().stream()
                .map(media -> new MediaRequest(
                        media.s3Key(),
                        media.mediaType(),
                        MediaOwner.BLOG,
                        blog.getId()
                )).toList();
        mediaService.saveAll(mediaRequests);

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.BLOG, blog.getId());
        return BlogResponse.from(blog, mediaList);
    }

    @Transactional(readOnly = true)
    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(blog -> {
                    List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.BLOG, blog.getId());
                    return BlogResponse.from(blog, mediaList);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public BlogResponse getBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.BLOG, blog.getId());
        return BlogResponse.from(blog, mediaList);
    }

    @Transactional(readOnly = true)
    public List<BlogResponse> getBlogsByAuthorId(Long authorId) {
        List<Blog> blogs = blogRepository.findAllByAuthorId(authorId);
        return blogs.stream()
                .map(blog -> {
                    List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.BLOG, blog.getId());
                    return BlogResponse.from(blog, mediaList);
                })
                .toList();
    }

    @Transactional
    public BlogResponse updateBlog(Long blogId, BlogRequest request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));

        // 기존 미디어 삭제
        mediaService.deleteAll(MediaOwner.BLOG, blog.getId());

        // 새 미디어 저장
        List<MediaRequest> newMediaRequests = request.contentMedia().stream()
                .map(media -> new MediaRequest(
                        media.s3Key(),
                        media.mediaType(),
                        MediaOwner.BLOG,
                        blog.getId()
                )).toList();
        mediaService.saveAll(newMediaRequests);

        blog.update(request.thumbnailImage(), request.title(), request.content());

        List<MediaResponse> mediaList = mediaService.findAll(MediaOwner.BLOG, blog.getId());
        return BlogResponse.from(blog, mediaList);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));

        mediaService.deleteAll(MediaOwner.BLOG, blog.getId());
        blogRepository.delete(blog);
    }
}
