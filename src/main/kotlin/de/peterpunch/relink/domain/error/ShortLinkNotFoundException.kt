package de.peterpunch.relink.domain.error

import org.springframework.http.HttpStatus

class ShortLinkNotFoundException(hash: String) :
    RelinkException(HttpStatus.NOT_FOUND, "ShortLink with hash $hash not found")
