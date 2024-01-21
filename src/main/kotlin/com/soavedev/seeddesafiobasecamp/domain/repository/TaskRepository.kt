package com.soavedev.seeddesafiobasecamp.domain.repository

import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TaskRepository : CrudRepository<Task, UUID> {
}