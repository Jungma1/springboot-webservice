package com.jungma.book.springboot.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HelloResponseTest {

    @Test
    void lombok_test() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponse dto = new HelloResponse(name, amount);

        // then
        // assertThat - 테스트 검증 라이브러리의 검증 메서
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
