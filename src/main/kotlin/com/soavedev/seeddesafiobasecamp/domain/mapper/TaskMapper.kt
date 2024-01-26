package com.soavedev.seeddesafiobasecamp.domain.mapper

import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class TaskMapper : Mapper<TaskDTO, Task> {
    override fun toEntity(domain: TaskDTO): Task {
        domain.validate().let {
            if (it.isNotEmpty()) {
                logger.error { "Failed to convert to entity" }
                throw IllegalArgumentException(it.toString())
            }
        }

        if(domain.id == null){
            logger.debug { "Generating ID for Task..." }
            domain.id = UUID.randomUUID()
        }

        return Task(
                id = domain.id!!,
                name = domain.name,
                startDate = domain.startDate!!,
                finishDate = domain.finishDate!!,
                status = domain.status.name,
                notes = domain.notes!!,
                userAssignId = domain.userAssignId!!.toString(),
                userNotifyId = domain.userNotifyId!!.toString(),
                bucketId = domain.bucketId,
                bucket = null
        )
    }

    override fun toDTO(entity: Task): TaskDTO {
        return TaskDTO(
                id = entity.id,
                name = entity.name,
                startDate = entity.startDate,
                finishDate = entity.finishDate,
                status = TaskStatus.valueOf(entity.status),
                notes = entity.notes,
                userAssignId = UUID.fromString(entity.userAssignId),
                userNotifyId = UUID.fromString(entity.userNotifyId),
                bucketId = entity.bucketId
        )
    }
}