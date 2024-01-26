package com.soavedev.seeddesafiobasecamp.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_task")
data class Task(

        @Id
        var id: UUID,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = true, unique = false)
        var startDate: LocalDateTime,

        @Column(nullable = true, unique = false)
        var finishDate: LocalDateTime,

        @Column(nullable = false, unique = false)
        var status: String,

        @Column(nullable = true, unique = false)
        var notes: String,

        @Column(nullable = true, unique = false)
        var userAssignId: String,

        @Column(nullable = true, unique = false)
        var userNotifyId: String,

        @Column(name = "bucket_id", nullable = false)
        var bucketId: String,

        @ManyToOne
        @JoinColumn(name = "bucket_id", insertable = false, updatable = false)
        var bucket: Bucket?

)