package de.peterpunch.relink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReLinkApplication

fun main(args: Array<String>) {
    runApplication<ReLinkApplication>(*args)
}
