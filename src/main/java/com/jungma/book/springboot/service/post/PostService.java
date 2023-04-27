package com.jungma.book.springboot.service.post;

import com.jungma.book.springboot.domain.post.Post;
import com.jungma.book.springboot.domain.post.PostRepository;
import com.jungma.book.springboot.web.dto.PostCreateRequest;
import com.jungma.book.springboot.web.dto.PostListResponse;
import com.jungma.book.springboot.web.dto.PostResponse;
import com.jungma.book.springboot.web.dto.PostUpdateRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostCreateRequest postCreateRequest) {
        return postRepository.save(postCreateRequest.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        return id;
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostResponse(post);
    }

    @Transactional(readOnly = true) // 조회 기능만 남겨두어 조회 속도 개선
    public List<PostListResponse> findAllDesc() {
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(PostListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        postRepository.delete(post);
    }
}
