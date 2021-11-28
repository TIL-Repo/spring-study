package me.hajoo.java.controller;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.java.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    /**
     * 모든 필터 적용
     * @return
     */
    @GetMapping("/")
    public String hello() {
        log.info("/ 진입");
        return "Hello";
    }

    /**
     * secondFilter 만 적용
     * @return
     */
    @GetMapping("/second-filter")
    public String hello2() {
        log.info("/second-filter 진입");
        return "Hello";
    }

    /**
     * thirdFilter 만 적용
     * @return
     */
    @GetMapping("/third-filter")
    public String hello3(@RequestBody UserDto userDto) {
        log.info(userDto.getName());
        log.info("/third-filter 진입");
        return "Hello";
    }

}
