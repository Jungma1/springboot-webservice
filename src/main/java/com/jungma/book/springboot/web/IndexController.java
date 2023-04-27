package com.jungma.book.springboot.web;

import com.jungma.book.springboot.service.post.PostService;
import com.jungma.book.springboot.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Model model) { // Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostResponse post = postService.findById(id);
        model.addAttribute("post", post);
        return "post-update";
    }
}
