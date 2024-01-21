package com.soavedev.seeddesafiobasecamp.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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
        var bucketId: String,

        @Column(nullable = true, unique = false)
        var groupId: String,

        @Column(nullable = true, unique = false)
        var userAssignId: String,

        @Column(nullable = true, unique = false)
        var userNotifyId: String
)