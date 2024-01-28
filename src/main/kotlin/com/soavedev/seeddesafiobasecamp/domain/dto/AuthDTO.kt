package com.soavedev.seeddesafiobasecamp.domain.dto

data class AuthDTO(
        var login: String,
        var password: String?,
        var token: String?
)