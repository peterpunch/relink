package de.peterpunch.relink.domain.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

const val CHARACTER_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_.~"

@Component
class HashGenerator(
    @Value("\${relink.hashgenerator.character-length}") private val characterLength: Int
) {
    fun generate(): String {
        return (1..characterLength)
            .map { CHARACTER_POOL.random() }
            .joinToString("")
    }
}
