package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import com.soavedev.seeddesafiobasecamp.service.TokenService
import com.soavedev.seeddesafiobasecamp.utils.DefaultBuilders
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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
        @MockBean val userRepository: UserRepository,
        val tokenService: TokenService,
        private val mockMvc: MockMvc,
        private val objectMapper: ObjectMapper
) {

    private var defaultUserRequest = DefaultBuilders.buildUserDto()
    private var randomUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
    private var user = DefaultBuilders.buildDefaultUserEntity()
    private lateinit var jwtToken: String

    @BeforeEach
    fun setup() {
        `when`(userRepository.findByLogin(user.login)).thenReturn(user)
        jwtToken = tokenService.generateToken(user)
    }

    @Test
    fun `should return HTTP 201 when create an user with success`() {
        `when`(userRepository.save(any())).thenReturn(user)

        mockMvc.perform(
                post(BASE_ENDPOINT)
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
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
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.cause").value(expectedMessage))
    }
}