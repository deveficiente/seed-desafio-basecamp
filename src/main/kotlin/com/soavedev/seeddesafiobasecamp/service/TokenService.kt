package com.soavedev.seeddesafiobasecamp.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.soavedev.seeddesafiobasecamp.domain.entity.User
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


private val logger = KotlinLogging.logger {}

@Service
class TokenService {

    // Mock as attribute because it is only for study
    // in a real case, this value comes from environment variables
    private var secret = "jwt-secret"

    fun generateToken(user: User): String {
        logger.debug { "Generating token for user [${user.login}]" }
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secret)
            val token: String = JWT.create()
                    .withIssuer("seed-desafio-basecamp-auth")
                    .withSubject(user.login)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm)
            logger.debug { "Token generated with success" }
            return token
        } catch (exception: JWTCreationException) {
            logger.error { "Failed to generate token." }
            throw RuntimeException("Error while generating token", exception)
        }
    }

    fun validateToken(token: String?): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                    .withIssuer("seed-desafio-basecamp-auth")
                    .build()
                    .verify(token)
                    .subject
        } catch (exception: JWTVerificationException) {
            return ""
        }
    }

    private fun genExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }
}