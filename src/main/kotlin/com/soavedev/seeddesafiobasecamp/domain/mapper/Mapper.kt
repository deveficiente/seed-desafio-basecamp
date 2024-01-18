package com.soavedev.seeddesafiobasecamp.domain.mapper

interface Mapper<D, E> {
    fun toEntity(domain: D): E
    fun toDTO(entity: E): D
}