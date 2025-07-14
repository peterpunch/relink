package de.peterpunch.relink.domain.entity

import de.peterpunch.relink.BaseIntegrationTest
import de.peterpunch.relink.domain.repository.ShortLinkRepo
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.net.URI

class ShortLinkTest @Autowired constructor(
    val shortLinkRepo: ShortLinkRepo
) : BaseIntegrationTest() {

    @Test
    fun `when save short link then persist with correct data`() {
        val destinationUrl = URI("https://www.google.com").toURL()
        val hash = "1234"

        val shortLink = transactionTemplate.execute {
            return@execute shortLinkRepo.save(
                ShortLink(
                    hash = hash,
                    destinationUrl = destinationUrl
                )
            )
        }
        Assertions.assertThat(shortLink).isNotNull
        Assertions.assertThat(shortLink?.id).isNotNull
        Assertions.assertThat(shortLink?.destinationUrl).isEqualTo(destinationUrl)
        Assertions.assertThat(shortLink?.hash).isEqualTo(hash)

        val shortLinkFromDB = transactionTemplate.execute {
            return@execute shortLinkRepo.findById(shortLink!!.id!!)
        }

        Assertions.assertThat(shortLinkFromDB).isPresent
        Assertions.assertThat(shortLinkFromDB?.get()).isNotNull
        Assertions.assertThat(shortLinkFromDB?.get()?.id).isEqualTo(shortLink?.id)
        Assertions.assertThat(shortLinkFromDB?.get()?.destinationUrl).isEqualTo(destinationUrl)
        Assertions.assertThat(shortLinkFromDB?.get()?.hash).isEqualTo(hash)
    }
}
