package com.soavedev.seeddesafiobasecamp.controller

import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.mapper.UserMapper
import com.soavedev.seeddesafiobasecamp.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
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
}