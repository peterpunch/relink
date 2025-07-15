package de.peterpunch.relink.controller

import de.peterpunch.relink.domain.error.ErrorMessage
import de.peterpunch.relink.domain.error.RelinkException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun handleGeneralException(exception: Exception): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.message
        )

        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    fun handleIllegalArgumentException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            exception.message
        )

        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleFintoolException(exception: RelinkException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            exception.status.value(),
            exception.message
        )
        return ResponseEntity(errorMessage, exception.status)
    }
}
