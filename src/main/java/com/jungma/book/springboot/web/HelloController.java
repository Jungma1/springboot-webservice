package com.jungma.book.springboot.web;

import com.jungma.book.springboot.web.dto.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponse helloResponse(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponse(name, amount);
    }
}
