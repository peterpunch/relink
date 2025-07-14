package de.peterpunch.relink.controller

import de.peterpunch.relink.domain.service.ShortLinkService
import de.peterpunch.relink.dto.CreateShortLinkDto
import de.peterpunch.relink.dto.ShortLinkDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
}
