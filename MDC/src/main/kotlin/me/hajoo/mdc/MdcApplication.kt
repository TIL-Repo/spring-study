package me.hajoo.mdc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class MdcApplication

fun main(args: Array<String>) {
    runApplication<MdcApplication>(*args)
}
