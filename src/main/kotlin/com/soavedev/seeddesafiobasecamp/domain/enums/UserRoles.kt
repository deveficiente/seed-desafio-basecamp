package com.soavedev.seeddesafiobasecamp.domain.enums

enum class UserRoles(private val role: String) {

    ADMIN("admin"),
    WRITER("writer"),
    READER("reader");

    fun getRole(): String{
        return role
    }
}