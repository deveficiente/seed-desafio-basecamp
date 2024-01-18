package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

private const val BASE_ENDPOINT = "/users"

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
        private val mockMvc: MockMvc,
        private val objectMapper: ObjectMapper
) {

    private lateinit var defaultUserRequest: UserDTO
    private var randomUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"

    @BeforeEach
    fun setup() {
        defaultUserRequest = buildUserDto()
    }

    @Test
    fun `should return HTTP 201 when create an user with success`() {
        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `when name is empty, should return HTTP 400 with message`() {
        val expectedMessage = "[name must be informed]"
        defaultUserRequest.name = ""

        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.cause").value(expectedMessage))
    }

    @Test
    fun `when name has more than 50 bytes, should return HTTP 400 with message`() {
        val expectedMessage = "[name must have max of 50 characters]"
        defaultUserRequest.name = "John Mayer John Mayer John Mayer John Mayer John Ma"

        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.cause").value(expectedMessage))
    }

    @Test
    fun `when email is empty, should return HTTP 400 with message`() {
        val expectedMessage = "[email must be informed]"
        defaultUserRequest.emailAddress = ""

        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.cause").value(expectedMessage))
    }

    @Test
    fun `when email is invalid, should return HTTP 400 with message`() {
        val expectedMessage = "[email must be valid]"
        defaultUserRequest.emailAddress = "someemailataoldotcom"

        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.cause").value(expectedMessage))
    }




    private fun buildUserDto(): UserDTO {
        return UserDTO(
                id = UUID.fromString(randomUUID),
                name = "John Mayer",
                emailAddress = "johnmayer@fender.com",
                role = UserRoles.ADMIN,
                status = UserStatus.ACTIVE,
                location = "Los Angeles, CA",
                shortBio = "Guitarrist and Taylor's Switft ex",
                profilePictureUrl = "blablabla"
        )
    }
}