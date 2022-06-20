package me.hajoo.aop._1_before.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {

    public void doSomething() {
        throw new NullPointerException();
    }
}
