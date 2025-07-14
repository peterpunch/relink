package de.peterpunch.relink.domain.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class HashGeneratorTest {

    var hashGenerator: HashGenerator? = null

    @Test
    fun `generate simple hash with 4 characters`() {
        hashGenerator = HashGenerator(4)

        val hash: String = hashGenerator!!.generate()

        Assertions.assertThat(hash).isNotNull
        Assertions.assertThat(hash).hasSize(4)
    }

    @Test
    fun `generate simple hash with only allowed characters`() {
        hashGenerator = HashGenerator(4)

        val hash: String = hashGenerator!!.generate()

        Assertions.assertThat(hash).isNotNull
        org.junit.jupiter.api.Assertions.assertTrue(
            hash.all { CHARACTER_POOL.contains(it) },
            "Hash $hash contains not allowed characters (see $CHARACTER_POOL)"
        )
    }
}
