package de.peterpunch.relink.domain.error

import org.springframework.http.HttpStatus

open class RelinkException(val status: HttpStatus, message: String) : RuntimeException(message) {

}
