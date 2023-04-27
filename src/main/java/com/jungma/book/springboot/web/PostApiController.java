package com.jungma.book.springboot.web;

import com.jungma.book.springboot.service.post.PostService;
import com.jungma.book.springboot.web.dto.PostCreateRequest;
import com.jungma.book.springboot.web.dto.PostResponse;
import com.jungma.book.springboot.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/v1/posts/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.save(postCreateRequest);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.update(id, postUpdateRequest);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }
}
