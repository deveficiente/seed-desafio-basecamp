package com.soavedev.seeddesafiobasecamp.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "tb_bucket")
class Bucket (

        @Id
        var id: UUID?,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var description: String,

        @Column(nullable = false)
        var createdBy: String,

        @Column(nullable = true)
        var startDate: LocalDateTime,

        @Column(nullable = true)
        var endDate: LocalDateTime,

        @OneToMany(mappedBy = "bucket", cascade = [CascadeType.ALL], orphanRemoval = true)
        var tasks: List<Task> = mutableListOf()

)