package com.soavedev.seeddesafiobasecamp.controller

import com.soavedev.seeddesafiobasecamp.domain.dto.BucketDTO
import com.soavedev.seeddesafiobasecamp.domain.mapper.BucketMapper
import com.soavedev.seeddesafiobasecamp.service.BucketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/buckets")
class BucketController @Autowired constructor(
        var bucketMapper: BucketMapper,
        var bucketService: BucketService
) {

    @PostMapping
    fun saveBucket(@RequestBody bucketDTO: BucketDTO): ResponseEntity<BucketDTO> {
        val bucketCreated = bucketService.saveBucket(bucketMapper.toEntity(bucketDTO))
        return ResponseEntity.status(HttpStatus.CREATED).body(bucketMapper.toDTO(bucketCreated))
    }

    @PutMapping
    fun updateBucket(@RequestBody bucketDTO: BucketDTO): ResponseEntity<BucketDTO>{
        val bucketUpdated = bucketService.updateBucket(bucketMapper.toEntity(bucketDTO))
        return ResponseEntity.status(HttpStatus.OK).body(bucketMapper.toDTO(bucketUpdated))
    }

    @GetMapping
    fun getAllBuckets(): ResponseEntity<List<BucketDTO>>{
        val buckets = bucketService.getAllBuckets()
        val bucketsDto = buckets.map { bucketMapper.toDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(bucketsDto)
    }

    @GetMapping("/{id-bucket}")
    fun getBucketById(@PathVariable("id-bucket") idBucket: String): ResponseEntity<BucketDTO> {
        val bucket = bucketService.getBucketById(idBucket)
        return ResponseEntity.status(HttpStatus.OK).body(bucketMapper.toDTO(bucket))
    }

}