package com.jungma.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 기능 활성화, @SpringBootApplication 과 분리하여 별도로 설정
@Configuration
public class JpaConfig {

}
