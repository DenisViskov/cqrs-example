package com.cqrs.cqrsexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CqrsExampleApplication

fun main(args: Array<String>) {
    runApplication<CqrsExampleApplication>(*args)
}
