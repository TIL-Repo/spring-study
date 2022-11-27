package me.hajoo.mdc.controller

import me.hajoo.mdc.common.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    companion object {
        const val HELLO_WORLD = "Hello World!"
    }

    @GetMapping
    fun hello() {
        logger().info(HELLO_WORLD)
        logger().warn(HELLO_WORLD)
        logger().error(HELLO_WORLD)
        logger().debug(HELLO_WORLD)
        logger().trace(HELLO_WORLD)
    }
}