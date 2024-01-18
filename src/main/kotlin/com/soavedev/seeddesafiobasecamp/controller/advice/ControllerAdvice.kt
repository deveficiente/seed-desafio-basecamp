package com.soavedev.seeddesafiobasecamp.controller.advice

import com.soavedev.seeddesafiobasecamp.controller.UserController
import org.springframework.http.ResponseEntity
import com.soavedev.seeddesafiobasecamp.domain.dto.ErrorResponse
import com.soavedev.seeddesafiobasecamp.domain.exceptions.EntityAlreadyExistsException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice(
        assignableTypes = [
            UserController::class
        ],
)
class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error("Handling IllegalArgumentException: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.BAD_REQUEST.reasonPhrase,
                        HttpStatus.BAD_REQUEST.value(),
                        exception.message,
                ),
                HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handleEntityAlreadyExistsException(exception: EntityAlreadyExistsException): ResponseEntity<ErrorResponse> {
        logger.error("Handling EntityAlreadyExistsException: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.BAD_REQUEST.reasonPhrase,
                        HttpStatus.BAD_REQUEST.value(),
                        exception.message,
                ),
                HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentInvalid(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error("Handling MethodArgumentNotValidException: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.BAD_REQUEST.reasonPhrase,
                        HttpStatus.BAD_REQUEST.value(),
                        exception.message,
                ),
                HttpStatus.BAD_REQUEST,
        )
    }
}