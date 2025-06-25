package com.example.lionking.domain.blog.controller;

import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.service.BlogService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
@Tag(name = "Blog API", description = "Session & Article")
public class BlogController {

    private final BlogService blogService;

    /**
     *  [CREATE]
     */
    @PostMapping("/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "블로그 작성", description = "새로운 블로그 게시글 작성")
    public ApiResponse<BlogResponse> postBlog(
            @RequestBody BlogRequest request,
            @PathVariable Long authorId
    ) {
        BlogResponse response = blogService.postBlog(request, authorId);
        return ApiResponse.success(response, "새로운 블로그 작성 성공");
    }

    /**
     * [READ]
     */
    @GetMapping("/{blogId}")
    @Operation(summary = "블로그 조회", description = "특정 블로그 ID 기반 게시글 조회")
    public ApiResponse<BlogResponse> getByBlogID(@PathVariable Long blogId) {
        BlogResponse response = blogService.getBlog(blogId);
        return ApiResponse.success(response, "블로그 ID 기반 조회 성공");
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "멤버 블로그 목록 조회", description = "특정 작성자의 모든 블로그 게시글을 조회")
    public ApiResponse<List<BlogResponse>> getBlogsByAuthorId(@PathVariable Long authorId) {
        List<BlogResponse> responses = blogService.getBlogsByAuthorId(authorId);
        return ApiResponse.success(responses, "작성자 ID 기반 블로그 목록 조회 성공");
    }

    /**
     * [UPDATE]
     */
    @PatchMapping("/{blogId}")
    @Operation(summary = "블로그 수정", description = "특정 블로그 ID 기반 제목, 본문, 이미지 수정")
    public ApiResponse<BlogResponse> updateBlog(
            @PathVariable Long blogId,
            @RequestBody BlogRequest request
    ) {
        BlogResponse response = blogService.updateBlog(blogId, request);
        return ApiResponse.success(response, "블로그 게시글 수정 성공");
    }

    /**
     * [DELETE]
     */
    @DeleteMapping("/{blogId}")
    @Operation(summary = "블로그 삭제", description = "특정 블로그 ID 기반 게시글 삭제")
    public ApiResponse<Void> deleteBlog(@PathVariable Long blogId) {
        blogService.deleteBlog(blogId);
        return ApiResponse.success(null, "블로그 게시글 삭제 성공");
    }

}
