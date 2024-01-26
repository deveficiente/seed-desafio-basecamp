package com.soavedev.seeddesafiobasecamp.domain.mapper

import com.soavedev.seeddesafiobasecamp.domain.dto.BucketDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Bucket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*

class BucketMapperTest {

    private lateinit var bucketDto: BucketDTO
    private lateinit var bucketEntity: Bucket

    var bucketMapper = BucketMapper()

    @BeforeEach
    fun setup() {
        bucketDto = buildBucketDto()
        bucketEntity = buildBucketEntity()
    }

    @Test
    fun `should map BucketDTO to Entity`() {
        val bucket = bucketMapper.toEntity(bucketDto)

        assertEquals(bucketDto.id, bucket.id)
        assertEquals(bucketDto.name, bucket.name)
    }

    @Test
    fun `should map Bucket to BucketDTO`() {
        val bucket = bucketMapper.toDTO(bucketEntity)

        assertEquals(bucketEntity.id, bucket.id)
        assertEquals(bucketEntity.name, bucket.name)
    }

    @Test
    fun `when ID is null, should set a new ID for bucket`() {
        bucketDto.id = null

        val bucket = bucketMapper.toEntity(bucketDto)

        assert(bucket.id.toString().isNotBlank())
    }

    @Test
    fun `when some data is invalid, should thrown IllegalArgumentException`() {
        bucketDto.name = ""

        assertThrows<IllegalArgumentException> {
            bucketMapper.toEntity(bucketDto)
        }
    }

    private fun buildBucketDto(): BucketDTO{
        return BucketDTO(
                id = UUID.randomUUID(),
                name = "Some name",
                description = "Some description",
                createdBy = "Some user",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
                taskIds = emptyList(),
        )
    }

    private fun buildBucketEntity(): Bucket {
        return Bucket(
                id = UUID.randomUUID(),
                name = "Some name",
                description = "Some description",
                createdBy = "Some user",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
                tasks = emptyList(),
        )
    }
}