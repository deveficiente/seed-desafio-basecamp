package com.soavedev.seeddesafiobasecamp.controller

import com.soavedev.seeddesafiobasecamp.domain.dto.TaskDTO
import com.soavedev.seeddesafiobasecamp.domain.mapper.TaskMapper
import com.soavedev.seeddesafiobasecamp.service.TaskService
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
@RequestMapping("/tasks")
class TaskController @Autowired constructor(
        private var taskMapper: TaskMapper,
        private var taskService: TaskService
) {

    @PostMapping
    fun saveTask(@RequestBody taskDTO: TaskDTO): ResponseEntity<TaskDTO> {
        val taskCreated = taskService.saveTask(taskMapper.toEntity(taskDTO))
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toDTO(taskCreated))
    }

    @PutMapping
    fun updateTask(@RequestBody taskDTO: TaskDTO): ResponseEntity<TaskDTO> {
        val taskUpdated = taskService.updateTask(taskMapper.toEntity(taskDTO))
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.toDTO(taskUpdated))
    }

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDTO>> {
        val tasks = taskService.getAllTasks()
        val tasksDto = tasks.map { taskMapper.toDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(tasksDto)
    }

    @GetMapping("/{task-id}")
    fun getTaskById(@PathVariable("task-id") taskId: String): ResponseEntity<TaskDTO> {
        val task = taskService.getTaskById(taskId)
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.toDTO(task))
    }
}