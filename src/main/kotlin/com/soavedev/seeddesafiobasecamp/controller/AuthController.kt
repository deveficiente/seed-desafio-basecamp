package com.soavedev.seeddesafiobasecamp.controller

import com.soavedev.seeddesafiobasecamp.domain.dto.AuthDTO
import com.soavedev.seeddesafiobasecamp.domain.dto.UserDTO
import com.soavedev.seeddesafiobasecamp.domain.entity.User
import com.soavedev.seeddesafiobasecamp.domain.mapper.UserMapper
import com.soavedev.seeddesafiobasecamp.service.TokenService
import com.soavedev.seeddesafiobasecamp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
        var authenticationManager: AuthenticationManager,
        var userService: UserService,
        var tokenService: TokenService,
        var userMapper: UserMapper,
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthDTO): ResponseEntity<AuthDTO> {
        val userLoginPassword = UsernamePasswordAuthenticationToken(authRequest.login, authRequest.password)

        val auth = authenticationManager.authenticate(userLoginPassword)

        authRequest.token = tokenService.generateToken(auth.principal as User)

        return ResponseEntity.status(HttpStatus.OK).body(authRequest)
    }

    @PostMapping("/register")
    fun saveUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val userCreated = userService.saveUser(userMapper.toEntity(userDTO))
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(userCreated))
    }
}