package com.jungma.book.springboot.domain.post;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    // 단위 테스트가 끝날 때마다 실행 (일반적으로 테스트간 데이터 충돌을 막기위해 사용)
    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    void saved_load_post() {
        // given
        String title = "Title";
        String content = "Content";

        // insert/update 쿼리를 실행, id 값이 있다면 update, 없다면 insert 쿼리를 실행
        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("test@naver.com")
                .build());

        // when
        List<Post> posts = postRepository.findAll(); // 모든 데이터 조회

        // then
        Post post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }
}
