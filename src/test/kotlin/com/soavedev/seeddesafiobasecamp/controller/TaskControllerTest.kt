package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import com.soavedev.seeddesafiobasecamp.domain.repository.TaskRepository
import com.soavedev.seeddesafiobasecamp.service.TaskService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.LocalDateTime
import java.util.*

private const val BASE_ENDPOINT = "/tasks"

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest @Autowired constructor(
        @InjectMocks val taskService: TaskService,
        @MockBean val taskRepository: TaskRepository,
        val objectMapper: ObjectMapper,
        val mockMvc: MockMvc
) {
    private var someUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
    private val defaultRequest = buildTaskDTO()
    private val taskEntity = buildTaskEntity()

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create a task with success`() {
        `when`(taskRepository.save(any())).thenReturn(taskEntity)

        val result = mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(result.response.status, HttpStatus.CREATED.value())
    }

    @Test
    fun `when task has no name, should return HTTP 400 with message`() {

        val messageExpected = "[name must be informed]"
        defaultRequest.name = ""

        val result = mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.response.status)
        assertEquals(messageExpected, JsonPath.read(result.response.contentAsString, "$.cause"))
    }

    @Test
    fun `when task name has more than 100 bytes, should return HTTP 400 with message`() {
        val messageExpected = "[name must have max of 100 characters]"
        defaultRequest.name = "some name some name some name some name some name some name some name some name some name some name s"

        val result = mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.response.status)
        assertEquals(messageExpected, JsonPath.read(result.response.contentAsString, "$.cause"))
    }

    @Test
    fun `should update a task with success`() {
        `when`(taskRepository.save(any())).thenReturn(taskEntity)
        `when`(taskRepository.findById(taskEntity.id)).thenReturn(Optional.of(taskEntity))

        val result = mockMvc.perform(
                put(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `should get all tasks with success`() {
        `when`(taskRepository.findAll()).thenReturn(listOf(taskEntity))

        val result = mockMvc.perform(
                get(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `should get a task by ID with success`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.of(taskEntity))

        val result = mockMvc.perform(
                get("$BASE_ENDPOINT/$someUUID")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `when did not found a task by ID, should return HTTP 404`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.empty())

        val result = mockMvc.perform(
                get("$BASE_ENDPOINT/$someUUID")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND.value(), result.response.status)
    }

    private fun buildTaskEntity(): Task {
        return Task(
                id = UUID.fromString(someUUID),
                name = "Example Task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now().plusDays(7),
                status =TaskStatus.BACKLOG.name,
                notes = "This is a sample task",
                bucketId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                groupId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                userAssignId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                userNotifyId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
        )
    }

    private fun buildTaskDTO(): TaskDTO {
        return TaskDTO(
                id = UUID.fromString(someUUID),
                name = "New task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now(),
                status = TaskStatus.BACKLOG,
                notes = "Some notes on my task",
                bucketId = UUID.randomUUID(),
                groupId = UUID.randomUUID(),
                userAssignId = UUID.randomUUID(),
                userNotifyId = UUID.randomUUID()
        )
    }
}