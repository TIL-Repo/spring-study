package me.hajoo.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/")
    public String hello() {
        log.info("/ 진입");
        return "Hello";
    }

    @GetMapping("/second-interceptor")
    public String hello2() {
        log.info("/second-interceptor 진입");
        return "Hello";
    }

    @GetMapping("/third/interceptor")
    public String hello3() {
        log.info("/third-interceptor 진입");
        return "Hello";
    }

}
