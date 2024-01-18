package com.soavedev.seeddesafiobasecamp.controller

import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.mapper.UserMapper
import com.soavedev.seeddesafiobasecamp.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
        private val userService: UserService,
        private val userMapper: UserMapper
) {

    @PostMapping
    fun saveUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val userCreated = userService.saveUser(userMapper.toEntity(userDTO))
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(userCreated))
    }

    @PutMapping("/{user-id}")
    fun updateUser(@RequestBody userDTO: UserDTO, @PathVariable("user-id") userId: String): ResponseEntity<UserDTO> {
        val userCreated = userService.updateUser(userMapper.toEntity(userDTO), userId)
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(userCreated))
    }

    @GetMapping("/{user-id}")
    fun getUserById(@PathVariable("user-id") userId: String): ResponseEntity<UserDTO> {
        val user = userService.getUserById(userId)
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(user))
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUsers()
        val usersDto = users.map { userMapper.toDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(usersDto)
    }
}