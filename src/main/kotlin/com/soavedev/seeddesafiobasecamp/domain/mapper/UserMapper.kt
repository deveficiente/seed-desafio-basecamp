package com.soavedev.seeddesafiobasecamp.domain.mapper

import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.User
import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class UserMapper: Mapper<UserDTO, User> {
    override fun toEntity(domain: UserDTO): User {

        domain.validate().let {
            if (it.isNotEmpty()) {
                logger.error { "Failed to convert to entity" }
                throw IllegalArgumentException(it.toString())
            }
        }

        if(domain.id == null){
            logger.debug { "Generating ID for new User..." }
            domain.id = UUID.randomUUID()
        }

        return User(
                id = domain.id!!,
                name = domain.name,
                login = domain.login,
                userPassword = domain.userPassword,
                emailAddress = domain.emailAddress,
                role = domain.role,
                location = domain.location,
                status = domain.status.name,
                shortBio = domain.shortBio,
                profilePictureUrl = domain.profilePictureUrl
        )
    }

    override fun toDTO(entity: User): UserDTO {
        return UserDTO(
                id = entity.id,
                name = entity.name,
                login = entity.login,
                userPassword = "*******************",
                emailAddress = entity.emailAddress,
                role = entity.role,
                status = UserStatus.valueOf(entity.status),
                location = entity.location,
                shortBio = entity.shortBio,
                profilePictureUrl = entity.shortBio
        )
    }
}