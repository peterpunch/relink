package de.peterpunch.relink.domain.service

import de.peterpunch.relink.domain.entity.ShortLink
import de.peterpunch.relink.domain.error.ShortLinkNotFoundException
import de.peterpunch.relink.domain.repository.ShortLinkRepo
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.net.URI
import java.time.OffsetDateTime
import kotlin.jvm.java

@ExtendWith(MockitoExtension::class)
class ShortLinkServiceTest {

    @InjectMocks
    private lateinit var shortLinkService: ShortLinkService

    @Mock
    private lateinit var hashGenerator: HashGenerator

    @Mock
    private lateinit var shortLinkRepo: ShortLinkRepo

    @Captor
    private lateinit var captor: ArgumentCaptor<ShortLink>

    @Test
    fun `createShortLink generates a valid link and saves it`() {
        val hash = "abcd"
        Mockito.`when`(hashGenerator.generate()).thenReturn(hash)
        Mockito.`when`(shortLinkRepo.existsShortLinkByHash(hash)).thenReturn(false)
        Mockito.`when`(shortLinkRepo.save(Mockito.any(ShortLink::class.java)))
            .thenAnswer { invocation -> return@thenAnswer invocation.getArgument<ShortLink>(0) }

        val url = "https://www.example.com"
        val shortLink: ShortLink = shortLinkService.createShortLink(url)

        Mockito.verify(hashGenerator).generate()
        Mockito.verify(shortLinkRepo).save(captor.capture())

        Assertions.assertThat(captor.value.destinationUrl.toString()).isEqualTo(url)
        Assertions.assertThat(captor.value.hash).isEqualTo(hash)
        Assertions.assertThat(captor.value).isSameAs(shortLink)
    }

    @Test
    fun `createShortLink checks for duplicates`() {
        val existingHash = "abcd"
        val secondHash = "1234"

        Mockito.`when`(hashGenerator.generate()).thenReturn(existingHash, secondHash)
        Mockito.`when`(shortLinkRepo.existsShortLinkByHash(existingHash)).thenReturn(true)
        Mockito.`when`(shortLinkRepo.existsShortLinkByHash(secondHash)).thenReturn(false)
        Mockito.`when`(shortLinkRepo.save(Mockito.any(ShortLink::class.java)))
            .thenAnswer { invocation -> return@thenAnswer invocation.getArgument<ShortLink>(0) }

        val url = "https://www.example.com"
        val shortLink: ShortLink = shortLinkService.createShortLink(url)

        Mockito.verify(hashGenerator, Mockito.times(2)).generate()
        Mockito.verify(shortLinkRepo).existsShortLinkByHash(existingHash)
        Mockito.verify(shortLinkRepo).existsShortLinkByHash(secondHash)
        Mockito.verify(shortLinkRepo).save(captor.capture())
        Assertions.assertThat(captor.value.hash).isEqualTo(secondHash)
        Assertions.assertThat(captor.value).isSameAs(shortLink)
    }

    @Test
    fun `getShortLinkByHash uses repo correctly`() {
        val hash = "ABCD"
        val expectedShortLink = ShortLink(
            hash = hash,
            destinationUrl = URI.create("https://example.com").toURL(),
            createdAt = OffsetDateTime.now(),
            id = 1
        )

        Mockito.`when`(shortLinkRepo.findShortLinkByHash(hash)).thenReturn(
            expectedShortLink
        )

        val shortLinkByHash = shortLinkService.getShortLinkByHash(hash)

        Mockito.verify(shortLinkRepo).findShortLinkByHash(hash)
        Assertions.assertThat(shortLinkByHash).isNotNull
        Assertions.assertThat(shortLinkByHash).isSameAs(expectedShortLink)
    }

    @Test
    fun `getShortLinkByHash throws ShortLinkNotFoundException`() {
        val hash = "ABCD"
        Mockito.`when`(shortLinkRepo.findShortLinkByHash(hash)).thenReturn(
            null
        )

        org.junit.jupiter.api.Assertions.assertThrows(
            ShortLinkNotFoundException::class.java,
            { shortLinkService.getShortLinkByHash(hash) }
        )
    }
}
