package com.soavedev.seeddesafiobasecamp.service

import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(
        private var userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(login: String): UserDetails {
        return userRepository.findByLogin(login)
    }
}