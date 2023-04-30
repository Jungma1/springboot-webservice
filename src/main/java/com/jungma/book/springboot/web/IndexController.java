package com.jungma.book.springboot.web;

import com.jungma.book.springboot.config.auth.LoginUser;
import com.jungma.book.springboot.config.auth.dto.SessionUser;
import com.jungma.book.springboot.service.post.PostService;
import com.jungma.book.springboot.web.dto.PostResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(@LoginUser SessionUser user, Model model) { // Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postService.findAllDesc());

        // 세션에 저장된 값이 있을 때만 model 에 userName 으로 등록
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
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
