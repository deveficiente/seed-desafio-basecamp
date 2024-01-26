package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import com.soavedev.seeddesafiobasecamp.domain.exceptions.NotFoundException
import com.soavedev.seeddesafiobasecamp.domain.repository.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.util.*

class TaskServiceTest {

    @Mock
    lateinit var taskRepository: TaskRepository

    @InjectMocks
    lateinit var taskService: TaskService

    private lateinit var taskDefault: Task

    private var someUUID = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1"

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        taskDefault = buildDefaultTask()
    }

    @Test
    fun `should save a task with success`() {
        `when`(taskRepository.save(any())).thenReturn(taskDefault)

        assertDoesNotThrow {
            taskService.saveTask(taskDefault)
        }
    }

    @Test
    fun `should update a task with success`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.of(taskDefault))
        `when`(taskRepository.save(any())).thenReturn(taskDefault)

        assertDoesNotThrow {
            taskService.updateTask(taskDefault)
        }
    }

    @Test
    fun `when try to update a non-existing task should throw NotFoundException`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {
            taskService.updateTask(taskDefault)
        }
    }

    @Test
    fun `should return all tasks`() {
        val listTasks = listOf(taskDefault)
        `when`(taskService.getAllTasks()).thenReturn(listTasks)

        val taskResult = taskService.getAllTasks()

        assert(taskResult.isNotEmpty())
    }

    @Test
    fun `should find a task by ID with success`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.of(taskDefault))

        val taskResult = taskService.getTaskById(someUUID)

        assertEquals(taskResult, taskDefault)
    }

    @Test
    fun `when task id does not exists, should throw NotFoundExceptions`() {
        `when`(taskRepository.findById(UUID.fromString(someUUID))).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {
            taskService.getTaskById(someUUID)
        }
    }

    private fun buildDefaultTask(): Task {
        return Task(
                id = UUID.fromString(someUUID),
                name = "Example Task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now().plusDays(7),
                status =TaskStatus.BACKLOG.name,
                notes = "This is a sample task",
                userAssignId = "user123",
                userNotifyId = "user456",
                bucketId = "6cb8d49c-6b07-4b69-8e5f-4c5b50115ee1",
                bucket = null
        )
    }
}