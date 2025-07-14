package de.peterpunch.relink.dto

import java.net.URL

data class ShortLinkDto(
    val url: URL,
    val hash: String
)
