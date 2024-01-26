package com.soavedev.seeddesafiobasecamp.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.Validation
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class BucketDTO (
        var id: UUID?,

        @field:NotEmpty(message = "name must be informed")
        @field:Size(max = 100, message = "name must have max of 100 characters")
        var name: String,

        @field:Size(max = 400, message = "description must have max of 400 characters")
        var description: String,

        @field:NotEmpty(message = "create user must be informed")
        var createdBy: String,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var startDate: LocalDateTime?,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var endDate: LocalDateTime?,

        var taskIds: List<String> = emptyList()
) {

        fun validate(): List<String> {
                val validator = Validation.buildDefaultValidatorFactory().validator
                return validator.validate(this).map { it.message }
        }
}