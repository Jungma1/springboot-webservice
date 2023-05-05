package com.jungma.book.springboot.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jungma.book.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HelloController.class, excludeFilters = {
        // SecurityConfig 는 읽었지만, SecurityConfig 를 생성하기 위해 필요한 CustomOAuth2UserService 는 읽을 수 없어서 오류가 발생한다.
        // 따라서, 스캔 대상에서 SecurityConfig 를 제거한다.
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    void hello_return() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // GET /hello 요청
                .andExpect(status().isOk()) // HTTP Header 의 Status 를 검증
                .andExpect(content().string(hello)); // 응답 본문의 내용을 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name) // 요청 파라미터를 설정(단, String 타입만 허용)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // JSON 응답값을 필드별로 검증할 수 있는 메서드
                .andExpect(jsonPath("$.amount", is(amount))); // $. 을 기준으로 필드명을 명시
    }
}
