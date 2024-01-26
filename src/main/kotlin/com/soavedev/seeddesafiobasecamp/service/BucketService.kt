package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import com.soavedev.seeddesafiobasecamp.domain.repository.BucketRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class BucketService @Autowired constructor(
        var bucketRepository: BucketRepository
) {

    fun saveBucket(bucket: Bucket): Bucket {
        logger.debug { "Saving Bucket with id [${bucket.id}]..." }
        return bucketRepository.save(bucket)
    }

    fun updateBucket(bucket: Bucket): Bucket {
        logger.debug { "Updating Bucket with ID [${bucket.id}]" }

        val bucketBefore = getBucketById(bucket.id.toString())
        logger.trace { "Bucket before: [$bucketBefore] | Bucket after: [$bucket]" }

        return bucketRepository.save(bucket)
    }

    fun getAllBuckets(): List<Bucket> {
        logger.debug { "Getting all Buckets..." }
        val buckets = bucketRepository.findAll().toList()
        return buckets
    }

    fun getBucketById(bucketId: String): Bucket {
        logger.debug { "Getting Bucket with ID [$bucketId]" }
        return bucketRepository
                .findById(UUID.fromString(bucketId))
                .orElseThrow { NotFoundException("Bucket $bucketId was not found" ) }
    }
}