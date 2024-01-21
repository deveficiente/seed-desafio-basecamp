package com.soavedev.seeddesafiobasecamp.controller.advice

import com.soavedev.seeddesafiobasecamp.controller.TaskController
import com.soavedev.seeddesafiobasecamp.controller.UserController
import org.springframework.http.ResponseEntity
import com.soavedev.seeddesafiobasecamp.domain.dto.ErrorResponse
import com.soavedev.seeddesafiobasecamp.domain.exceptions.BadRequestException
import com.soavedev.seeddesafiobasecamp.domain.exceptions.EntityAlreadyExistsException
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice(
        assignableTypes = [
            UserController::class,
            TaskController::class
        ],
)
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ErrorResponse> {
        logger.error("Handling BadRequestException: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.BAD_REQUEST.reasonPhrase,
                        HttpStatus.BAD_REQUEST.value(),
                        exception.message,
                ),
                HttpStatus.BAD_REQUEST,
        )
    }

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

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<ErrorResponse> {
        logger.error("Handling NotFoundException: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.NOT_FOUND.reasonPhrase,
                        HttpStatus.NOT_FOUND.value(),
                        exception.message,
                ),
                HttpStatus.NOT_FOUND,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyKindOfGenericException(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Handling Exception: ${exception.message}", exception)
        return ResponseEntity(
                ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        exception.message,
                ),
                HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}