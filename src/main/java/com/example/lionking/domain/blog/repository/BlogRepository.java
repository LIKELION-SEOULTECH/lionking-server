package com.example.lionking.domain.blog.repository;

import com.example.lionking.domain.blog.entitiy.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
