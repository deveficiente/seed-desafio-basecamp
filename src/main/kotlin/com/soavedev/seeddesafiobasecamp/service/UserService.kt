package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.entity.User
import com.soavedev.seeddesafiobasecamp.domain.exceptions.EntityAlreadyExistsException
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository
) {

    fun saveUser(user: User): User {
        logger.debug { "Saving User with id [${user.id}]..." }
        validateIfEmailAlreadyExists(user.emailAddress)
        return userRepository.save(user)
    }

    fun updateUser(user: User): User {
        logger.debug { "Updating User with id [${user.id}]..." }
        getUserById(user.id.toString())
        return userRepository.save(user)
    }

    fun getAllUsers(): List<User> {
        logger.debug { "Getting all users..." }
        return userRepository.findAll().toList()
    }

    fun getUserById(userId: String): User {
        logger.debug { "Getting user with if [$userId]" }
        return userRepository
                .findById(UUID.fromString(userId))
                .orElseThrow{ NotFoundException("User $userId was not found") }
    }

    fun validateIfEmailAlreadyExists(emailAddress: String) {
        logger.debug { "Searching email address [$emailAddress]..." }

        val user = userRepository.findUserByEmailAddress(emailAddress)

        if (user.isPresent){
            throw EntityAlreadyExistsException("User with  email [$emailAddress] already exists")
        }
    }
}
