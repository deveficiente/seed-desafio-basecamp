package com.soavedev.seeddesafiobasecamp.utils

import com.soavedev.seeddesafiobasecamp.domain.dto.AuthDTO
import com.soavedev.seeddesafiobasecamp.domain.dto.BucketDTO
import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.entity.User
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import com.soavedev.seeddesafiobasecamp.domain.enums.UserStatus
import java.time.LocalDateTime
import java.util.*

object DefaultBuilders {

    private val someUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"

    fun buildBucketDto(): BucketDTO {
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

    fun buildBucketEntity(): Bucket {
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

    fun buildUserDto(): UserDTO {
        return UserDTO(
                id = UUID.fromString(someUUID),
                name = "John Mayer",
                login = "username",
                userPassword = "some pass",
                emailAddress = "johnmayer@fender.com",
                role = UserRoles.ADMIN,
                status = UserStatus.ACTIVE,
                location = "Los Angeles, CA",
                shortBio = "Guitarrist and Taylor's Switft ex",
                profilePictureUrl = "blablabla"
        )
    }

    fun buildDefaultUserEntity(): User {
        return User(
                id = UUID.fromString(someUUID),
                name = "John Mayer",
                login = "username",
                userPassword = "some pass",
                emailAddress = "johnmayer@guitar.com",
                role = UserRoles.ADMIN,
                status = UserStatus.ACTIVE.name,
                location = "Los Angeles, CA",
                shortBio = "Guitarrist and Taylor Swift ex",
                profilePictureUrl = "blablabla"
        )
    }

    fun buildTaskEntity(): Task {
        return Task(
                id = UUID.fromString(someUUID),
                name = "Example Task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now().plusDays(7),
                status = TaskStatus.BACKLOG.name,
                notes = "This is a sample task",
                userAssignId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                userNotifyId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                bucketId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                bucket = null
        )
    }

    fun buildTaskDTO(): TaskDTO {
        return TaskDTO(
                id = UUID.fromString(someUUID),
                name = "New task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now(),
                status = TaskStatus.BACKLOG,
                notes = "Some notes on my task",
                userAssignId = UUID.randomUUID(),
                userNotifyId = UUID.randomUUID(),
                bucketId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"
        )
    }

    fun buildAuthRequest(): AuthDTO {
        return AuthDTO(
                login = "username",
                password = "some pass",
                token = null
        )
    }
}