package de.peterpunch.relink.controller

import de.peterpunch.relink.domain.service.ShortLinkService
import de.peterpunch.relink.dto.CreateShortLinkDto
import de.peterpunch.relink.dto.ShortLinkDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/relink/api/")
class ShortLinkController(
    val shortLinkService: ShortLinkService
) {
    @PostMapping(
        value = ["short-links"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun postShortLink(@RequestBody createShortLinkDto: CreateShortLinkDto): ShortLinkDto {
        val shortLink = shortLinkService.createShortLink(createShortLinkDto.url.toString())

        return ShortLinkDto(url = shortLink.destinationUrl, hash = shortLink.hash)
    }

    @GetMapping(
        value = ["short-links/{hash}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getShortLink(@PathVariable(required = true) hash: String): ShortLinkDto {
        val shortLink = shortLinkService.getShortLinkByHash(hash)
        return ShortLinkDto(url = shortLink.destinationUrl, hash = shortLink.hash)
    }
}
