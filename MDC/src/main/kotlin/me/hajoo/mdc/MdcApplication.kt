package me.hajoo.mdc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MdcApplication

fun main(args: Array<String>) {
    runApplication<MdcApplication>(*args)
}
