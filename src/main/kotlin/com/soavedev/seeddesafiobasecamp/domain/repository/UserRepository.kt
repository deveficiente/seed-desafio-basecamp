package com.soavedev.seeddesafiobasecamp.domain.repository

import com.soavedev.seeddesafiobasecamp.domain.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {

    fun findUserByEmailAddress(emailAddress: String): Optional<User>
}