package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import com.soavedev.seeddesafiobasecamp.domain.repository.TaskRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class TaskService @Autowired constructor(
        private var taskRepository: TaskRepository
) {

    fun saveTask(task: Task): Task {
        logger.debug { "Saving Task with id [${task.id}]..." }
        return taskRepository.save(task)
    }

    fun updateTask(task: Task): Task {
        logger.debug { "Updating Task with ID [${task.id}]" }

        val taskBefore = getTaskById(task.id.toString())
        logger.trace { "Task before: [$taskBefore] | Task after: [$task]" }

        return taskRepository.save(task)
    }

    fun getAllTasks(): List<Task> {
        logger.debug { "Getting all tasks..." }
        return taskRepository.findAll().toList()
    }

    fun getTaskById(taskId: String): Task {
        logger.debug { "Getting task with ID [$taskId]" }
        return taskRepository
                .findById(UUID.fromString(taskId))
                .orElseThrow { NotFoundException("Task $taskId was not found" ) }
    }
}