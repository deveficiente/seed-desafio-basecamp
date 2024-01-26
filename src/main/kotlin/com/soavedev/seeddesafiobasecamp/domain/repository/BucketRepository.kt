package com.soavedev.seeddesafiobasecamp.domain.repository

import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BucketRepository : CrudRepository<Bucket, UUID> {
}