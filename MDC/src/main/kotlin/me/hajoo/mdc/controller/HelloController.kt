package me.hajoo.mdc.controller

import me.hajoo.mdc.common.logger
import me.hajoo.mdc.service.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
        private val helloService: HelloService
) {

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
        helloService.hello()
    }
}