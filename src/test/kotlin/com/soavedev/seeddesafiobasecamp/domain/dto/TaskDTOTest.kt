package com.soavedev.seeddesafiobasecamp.domain.dto

import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class TaskDTOTest {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    private lateinit var taskDefault: TaskDTO

    @BeforeEach
    fun setup() {
        taskDefault = buildTaskDefault()
    }

    @Test
    fun `when all data is filled should create a DTO with data without messages`() {
        val violations = validator.validate(taskDefault)

        assert(violations.isEmpty())
    }

    @Test
    fun `when name is empty, should return message on validator`() {
        val messageExpected = "name must be informed"

        taskDefault.name = ""

        val violations = validator.validate(taskDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "name")
        assert(violations.first().message.toString() == messageExpected)
    }

    @Test
    fun `when name has more than 100 bytes, should return message on validator`() {
        val messageExpected = "name must have max of 100 characters"

        taskDefault.name = "Esmeralda Constância da Silva Pereira Oliveira Santos Rodrigues da Cunha Gonzaga Souza Lima Almeida Costa Peregrino" +
                "Esmeralda Constância da Silva Pereira Oliveira Santos Rodrigues da Cunha Gonzaga Souza Lima Almeida Costa Peregrino" +
                "Esmeralda Constância da Silva Pereira Oliveira Santos Rodrigues da Cunha Gonzaga Souza Lima Almeida Costa Peregrino"

        val violations = validator.validate(taskDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "name")
        assert(violations.first().message.toString() == messageExpected)
    }

    private fun buildTaskDefault(): TaskDTO {
        return TaskDTO(
                id = UUID.randomUUID(),
                name = "New task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now(),
                status = TaskStatus.BACKLOG,
                notes = "Some notes on my task",
                userAssignId = UUID.randomUUID(),
                userNotifyId = UUID.randomUUID(),
                bucketId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
        )
    }

}