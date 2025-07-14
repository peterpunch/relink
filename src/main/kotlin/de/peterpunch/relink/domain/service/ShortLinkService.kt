package de.peterpunch.relink.domain.service

import de.peterpunch.relink.domain.entity.ShortLink
import de.peterpunch.relink.domain.error.ShortLinkNotFoundException
import de.peterpunch.relink.domain.repository.ShortLinkRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ShortLinkService(
    val hashGenerator: HashGenerator,
    val shortLinkRepo: ShortLinkRepo
) {
    @Transactional
    fun createShortLink(url: String): ShortLink {
        var hash = hashGenerator.generate()

        while (shortLinkRepo.existsShortLinkByHash(hash)) {
            hash = hashGenerator.generate()
        }

        return shortLinkRepo.save(
            ShortLink(
                hash = hash, destinationUrl = URI(url).toURL()
            )
        )
    }

    fun getShortLinkByHash(hash: String): ShortLink {
        return shortLinkRepo.findShortLinkByHash(hash) ?: throw ShortLinkNotFoundException(hash)
    }
}
