package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.entity.User
import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import com.soavedev.seeddesafiobasecamp.domain.exceptions.EntityAlreadyExistsException
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class UserServiceTest {

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userService: UserService

    private lateinit var userDefault: User

    private var randomUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userDefault = buildDefaultUserEntity()
    }

    @Test
    fun `should save a user with success`() {
        `when`(userRepository.save(any())).thenReturn(userDefault)
        assertDoesNotThrow {
            userService.saveUser(userDefault)
        }
    }

    @Test
    fun `when try to save a user that have a existing email, should throw EntityAlreadyExistsException`() {
        `when`(userService.validateIfEmailAlreadyExists(anyString())).thenThrow(EntityAlreadyExistsException(""))
        assertThrows<EntityAlreadyExistsException> {
            userService.saveUser(userDefault)
        }
    }

    @Test
    fun `should update a user with success`() {
        `when`(userRepository.save(any())).thenReturn(userDefault)
        assertDoesNotThrow {
            userService.saveUser(userDefault)
        }
    }

    @Test
    fun `when try to update a non-existing user, should throw NotFoundException`() {
        `when`(userRepository.findById(UUID.fromString(randomUUID))).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {
            userService.updateUser(userDefault)
        }
    }

    @Test
    fun `should return all users`() {
        val listUsers = listOf(userDefault)
        `when`(userService.getAllUsers()).thenReturn(listUsers)

        val userResult = userService.getAllUsers()

        assert(userResult.isNotEmpty())
    }

    @Test
    fun `should return a user by id with sucess`() {
        `when`(userRepository.findById(UUID.fromString(randomUUID))).thenReturn(Optional.of(userDefault))

        val userResult = userService.getUserById(randomUUID)

        assertEquals(userResult, userDefault)
    }

    @Test
    fun `when ID does not exists, shoud throw NotFoundException`() {
        `when`(userRepository.findById(UUID.fromString(randomUUID))).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {
            userService.getUserById(randomUUID)
        }
    }

    private fun buildDefaultUserEntity(): User {
        return User(
                id = UUID.fromString(randomUUID),
                name = "John Mayer",
                emailAddress = "johnmayer@guitar.com",
                role = UserRoles.ADMIN.name,
                status = UserStatus.ACTIVE.name,
                location = "Los Angeles, CA",
                shortBio = "Guitarrist and Taylor Swift ex",
                profilePictureUrl = "blablabla"
        )
    }
}