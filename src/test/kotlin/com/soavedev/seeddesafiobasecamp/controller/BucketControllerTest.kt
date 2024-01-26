package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.soavedev.seeddesafiobasecamp.domain.dto.BucketDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import com.soavedev.seeddesafiobasecamp.domain.repository.BucketRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.LocalDateTime
import java.util.*

private const val BASE_ENDPOINT = "/buckets"

@SpringBootTest
@AutoConfigureMockMvc
class BucketControllerTest @Autowired constructor(
        @MockBean val bucketRepository: BucketRepository,
        val objectMapper: ObjectMapper,
        val mockMvc: MockMvc
) {

    private val someUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
    private val defaultRequest = buildBucketDto()
    private val bucketEntity = buildBucketEntity()

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create a bucket with success`() {
        `when`(bucketRepository.save(any())).thenReturn(bucketEntity)

        val result = mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(result.response.status, HttpStatus.CREATED.value())
    }

    @Test
    fun `when bucket has no name, should return HTTP 400 with message`() {
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
    fun `when bucket has no create user ID, should return HTTP 400 with message`() {
        val messageExpected = "[create user must be informed]"
        defaultRequest.createdBy = ""

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
    fun `when bucket name has more than 100 bytes, should return HTTP 400 with message`() {
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
    fun `when bucket description has more than 400 bytes, should return HTTP 400 with message`() {
        val messageExpected = "[description must have max of 400 characters]"
        defaultRequest.description = "some name some name some name some name some name some name some name some name some name some name s" +
                "some name some name some name some name some name some name some name some name some name some name s" +
                "some name some name some name some name some name some name some name some name some name some name s" +
                "some name some name some name some name some name some name some name some name some name some name s" +
                "some name some name some name some name some name some name some name some name some name some name s"

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
    fun `should update a bucket with success`() {
        `when`(bucketRepository.save(any())).thenReturn(bucketEntity)
        `when`(bucketRepository.findById(bucketEntity.id!!)).thenReturn(Optional.of(bucketEntity))

        val result = mockMvc.perform(
                MockMvcRequestBuilders.put(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultRequest))
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `should get all buckets with success`() {
        `when`(bucketRepository.findAll()).thenReturn(listOf(bucketEntity))

        val result = mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `should get a bucket by ID with success`() {
        `when`(bucketRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.of(bucketEntity))

        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("$BASE_ENDPOINT/$someUUID")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.OK.value(), result.response.status)
    }

    @Test
    fun `when did not found a bucket by ID, should return HTTP 404`() {
        `when`(bucketRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.empty())

        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("$BASE_ENDPOINT/$someUUID")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND.value(), result.response.status)
    }

    private fun buildBucketDto(): BucketDTO {
        return BucketDTO(
                id = UUID.fromString(someUUID),
                name = "Some name",
                description = "Some description",
                createdBy = "Some user",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
                taskIds = emptyList(),
        )
    }

    private fun buildBucketEntity(): Bucket {
        return Bucket(
                id = UUID.fromString(someUUID),
                name = "Some name",
                description = "Some description",
                createdBy = "Some user",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
                tasks = emptyList(),
        )
    }
}