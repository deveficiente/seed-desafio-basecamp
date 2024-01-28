package com.soavedev.seeddesafiobasecamp.domain.dto

import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class UserDTOTest {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    private lateinit var userDefault: UserDTO

    @BeforeEach
    fun setup() {
        userDefault = buildDefaultUserDTO()
    }

    @Test
    fun `when all data is filled should create a user DTO with data correctly`() {
        val violations = validator.validate(userDefault)

        assert(violations.isEmpty())
    }

    @Test
    fun `when name is empty, should return message on validator`() {
        val messageExpected = "name must be informed"

        userDefault.name = ""

        val violations = validator.validate(userDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "name")
        assert(violations.first().message.toString() == messageExpected)

    }

    @Test
    fun `when name has more than 50 bytes, should return message on validator`() {
        val messageExpected = "name must have max of 50 characters"

        userDefault.name = "Esmeralda Constância da Silva Pereira Oliveira Santos Rodrigues da Cunha Gonzaga Souza Lima Almeida Costa Peregrino"

        val violations = validator.validate(userDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "name")
        assert(violations.first().message.toString() == messageExpected)
    }

    @Test
    fun `when email is empty should return message on validator`() {
        val messageExpected = "email must be informed"

        userDefault.emailAddress = ""

        val violations = validator.validate(userDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "emailAddress")
        assert(violations.first().message.toString() == messageExpected)
    }

    @Test
    fun `when email has a invalid format, should return message on validator`() {
        val messageExpected = "email must be valid"

        userDefault.emailAddress = "johnmayeratguitardotcom"

        val violations = validator.validate(userDefault)

        assert(violations.isNotEmpty())
        assert(violations.first().propertyPath.toString() == "emailAddress")
        assert(violations.first().message.toString() == messageExpected)
    }

    private fun buildDefaultUserDTO(): UserDTO {
        return UserDTO(
                id = UUID.randomUUID(),
                name = "John Mayer",
                login = "username",
                userPassword = "some pass",
                emailAddress = "johnmayer@guitar.com",
                role = UserRoles.ADMIN,
                status = UserStatus.ACTIVE,
                location = "Los Angeles, CA",
                shortBio = "Guitarrist and Taylor Swift ex",
                profilePictureUrl = "blablabla"
        )
    }
}