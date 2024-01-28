package com.soavedev.seeddesafiobasecamp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import com.soavedev.seeddesafiobasecamp.service.TokenService
import com.soavedev.seeddesafiobasecamp.utils.DefaultBuilders
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val BASE_ENDPOINT = "/auth"

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest @Autowired constructor(
        @MockBean val userRepository: UserRepository,
        val tokenService: TokenService,
        private val mockMvc: MockMvc,
        private val objectMapper: ObjectMapper
) {

    private var defaultUserRequest = DefaultBuilders.buildUserDto()
    private var loginRequest = DefaultBuilders.buildAuthRequest()
    private var user = DefaultBuilders.buildDefaultUserEntity()
    private lateinit var jwtToken: String

    @BeforeEach
    fun setup() {
        `when`(userRepository.findByLogin(user.login)).thenReturn(user)
        jwtToken = tokenService.generateToken(user)
    }

    @Test
    fun `when try to register a new user, should return success`() {
        `when`(userRepository.save(ArgumentMatchers.any())).thenReturn(user)

        mockMvc.perform(
                post("$BASE_ENDPOINT/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(defaultUserRequest))
        )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `when try to login with user, should return success with token`() {
        // TODO
    }

    @Test
    fun `when login already exists, should return BAD REQUEST`() {
        // TODO
    }

    @Test
    fun `when credentials are invalid, should return FORBIDDEN`() {
        // TODO
    }


}