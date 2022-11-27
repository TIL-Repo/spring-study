package me.hajoo.mdc.service

import me.hajoo.mdc.common.logger
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class HelloService {

    @Async
    fun hello() {
        logger().info("Hello Service!")
    }
}