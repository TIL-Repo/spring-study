package me.hajoo.aop._1_before.controller;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.aop._1_before.annotation.RecordLog;
import me.hajoo.aop._1_before.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    @RecordLog
    @GetMapping("/")
    public String hello() {
        log.info("/ 진입");
        try {
            testService.doSomething();
        } catch (NullPointerException e) {}
        return "hello";
    }

    @GetMapping("/second")
    public String hello2(String param1) {
        log.info("/second 진입");
        return "hello2";
    }

    @GetMapping("/third")
    public String hello2(String param1, String param2) {
        log.info("/third 진입");
        return "hello2";
    }

}
