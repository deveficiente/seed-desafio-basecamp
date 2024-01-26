package com.soavedev.seeddesafiobasecamp.domain.mapper

import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.Task
import com.soavedev.seeddesafiobasecamp.domain.enums.TaskStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class TaskMapperTest {

    private lateinit var taskDtoDefault: TaskDTO
    private lateinit var taskEntityDefault: Task

    private val taskMapper = TaskMapper()

    @BeforeEach
    fun setup() {
        taskDtoDefault = buildDefaultTaskDTO()
        taskEntityDefault = buildDefaultTaskEntity()
    }

    @Test
    fun `should map TaskDTO to Task`() {
        val task = taskMapper.toEntity(taskDtoDefault)

        assertEquals(taskDtoDefault.id, task.id)
        assertEquals(taskDtoDefault.name, task.name)
        assertEquals(taskDtoDefault.startDate, task.startDate)
        assertEquals(taskDtoDefault.finishDate, task.finishDate)
        assertEquals(taskDtoDefault.status, TaskStatus.valueOf(task.status))
        assertEquals(taskDtoDefault.notes, task.notes)
        assertEquals(taskDtoDefault.userAssignId, UUID.fromString(task.userAssignId))
        assertEquals(taskDtoDefault.userNotifyId, UUID.fromString(task.userNotifyId))
        assertEquals(taskDtoDefault.bucketId, task.bucketId)
    }

    @Test
    fun `should map Task to TaskDTO`() {
        val taskDto = taskMapper.toDTO(taskEntityDefault)

        assertEquals(taskEntityDefault.id, taskDto.id)
        assertEquals(taskEntityDefault.name, taskDto.name)
        assertEquals(taskEntityDefault.startDate, taskDto.startDate)
        assertEquals(taskEntityDefault.finishDate, taskDto.finishDate)
        assertEquals(TaskStatus.valueOf(taskEntityDefault.status), taskDto.status)
        assertEquals(taskEntityDefault.notes, taskDto.notes)
        assertEquals(UUID.fromString(taskEntityDefault.userAssignId), taskDto.userAssignId)
        assertEquals(UUID.fromString(taskEntityDefault.userNotifyId), taskDto.userNotifyId)
        assertEquals(taskEntityDefault.bucketId, taskDto.bucketId)
    }

    @Test
    fun `when ID is null, should set a new UUID for the task`() {
        taskDtoDefault.id = null

        val task = taskMapper.toEntity(taskDtoDefault)

        assert(task.id.toString().isNotBlank())
    }

    @Test
    fun `when some data is invalid, should throw IllegalArgumentException`() {
        taskDtoDefault.name = ""

        assertThrows<IllegalArgumentException> {
            taskMapper.toEntity(taskDtoDefault)
        }
    }

    private fun buildDefaultTaskDTO(): TaskDTO {
        return TaskDTO(
                id = UUID.randomUUID(),
                name = "Sample Task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now().plusHours(1),
                status = TaskStatus.IN_PROGRESS,
                notes = "Sample notes",
                userAssignId = UUID.randomUUID(),
                userNotifyId = UUID.randomUUID(),
                bucketId = UUID.randomUUID().toString()
        )
    }

    private fun buildDefaultTaskEntity(): Task {
        return Task(
                id = UUID.randomUUID(),
                name = "Sample Task",
                startDate = LocalDateTime.now(),
                finishDate = LocalDateTime.now().plusHours(1),
                status = TaskStatus.IN_PROGRESS.name,
                notes = "Sample notes",
                userAssignId = UUID.randomUUID().toString(),
                userNotifyId = UUID.randomUUID().toString(),
                bucketId = UUID.randomUUID().toString(),
                bucket = null
        )
    }
}