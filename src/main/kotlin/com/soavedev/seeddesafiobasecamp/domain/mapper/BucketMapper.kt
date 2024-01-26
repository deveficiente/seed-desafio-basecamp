package com.soavedev.seeddesafiobasecamp.domain.mapper

import com.soavedev.seeddesafiobasecamp.domain.dto.BucketDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class BucketMapper : Mapper<BucketDTO, Bucket> {
    override fun toEntity(domain: BucketDTO): Bucket {
        domain.validate().let {
            if (it.isNotEmpty()) {
                logger.error { "Failed to convert to entity" }
                throw IllegalArgumentException(it.toString())
            }
        }

        if(domain.id == null){
            logger.debug { "Generating ID for Bucket..." }
            domain.id = UUID.randomUUID()
        }

        return Bucket(
                id = domain.id!!,
                name = domain.name,
                description = domain.description,
                createdBy = domain.createdBy,
                startDate = domain.startDate!!,
                endDate = domain.endDate!!
        )

    }

    override fun toDTO(entity: Bucket): BucketDTO {
        return BucketDTO(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                createdBy = entity.createdBy,
                startDate = entity.startDate,
                endDate = entity.endDate,
                taskIds = entity.tasks.map { it.id.toString() }

        )
    }
}