package de.peterpunch.relink.domain.service

import de.peterpunch.relink.BaseIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class HashGeneratorIntegrationTest @Autowired constructor(val hashGenerator: HashGenerator) :
    BaseIntegrationTest() {

    @Test
    fun `generate should generate a hash with 4 characters`() {
        val hash = hashGenerator.generate()

        Assertions.assertThat(hash).hasSize(4)
    }

    @Test
    fun `generate simple hash with only allowed characters`() {
        val hash: String = hashGenerator.generate()

        Assertions.assertThat(hash).isNotNull
        org.junit.jupiter.api.Assertions.assertTrue(
            hash.all { CHARACTER_POOL.contains(it) },
            "Hash $hash contains not allowed characters (see $CHARACTER_POOL)"
        )
    }
}
