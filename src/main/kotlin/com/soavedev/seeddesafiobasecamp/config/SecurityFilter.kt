package com.soavedev.seeddesafiobasecamp.config

import com.soavedev.seeddesafiobasecamp.domain.repository.UserRepository
import com.soavedev.seeddesafiobasecamp.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class SecurityFilter @Autowired constructor(
        var tokenService: TokenService,
        var userRepository: UserRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = this.recoverToken(request)
        if (token != null) {
            val login = tokenService.validateToken(token)
            val user = userRepository.findByLogin(login)

            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null
        return authHeader.replace("Bearer ", "")
    }
}