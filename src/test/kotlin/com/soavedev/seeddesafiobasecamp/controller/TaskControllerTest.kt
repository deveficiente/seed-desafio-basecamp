package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import com.soavedev.seeddesafiobasecamp.domain.repository.TaskRepository
import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import com.soavedev.seeddesafiobasecamp.service.TaskService
import com.soavedev.seeddesafiobasecamp.service.TokenService
import com.soavedev.seeddesafiobasecamp.utils.DefaultBuilders
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
        @MockBean val taskRepository: TaskRepository,
        @MockBean val userRepository: UserRepository,
        val tokenService: TokenService,
        val objectMapper: ObjectMapper,
        val mockMvc: MockMvc
) {
    private var someUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
    private val defaultRequest = DefaultBuilders.buildTaskDTO()
    private val taskEntity = DefaultBuilders.buildTaskEntity()
    private val user = DefaultBuilders.buildDefaultUserEntity()
    private lateinit var jwtToken: String

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(userRepository.findByLogin(user.login)).thenReturn(user)
        jwtToken = tokenService.generateToken(user)
    }

    @Test
    fun `should create a task with success`() {
        `when`(taskRepository.save(any())).thenReturn(taskEntity)

        val result = mockMvc.perform(
                post(BASE_ENDPOINT)
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND.value(), result.response.status)
    }
}