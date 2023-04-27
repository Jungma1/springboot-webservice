package com.jungma.book.springboot.web.dto;

import com.jungma.book.springboot.domain.post.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostListResponse {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostListResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.modifiedDate = post.getLastModifiedDate();
    }
}
