package com.soavedev.seeddesafiobasecamp.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration @Autowired constructor(
        var securityFilter: SecurityFilter
) {

    @Bean
    fun securityChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
                .csrf { csrf -> csrf.disable() }
                .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .authorizeHttpRequests { auth -> auth
                    .requestMatchers(HttpMethod.POST,
                            "/auth/login",
                            "/auth/register").permitAll()
                    .requestMatchers(
                            "/buckets",
                            "/tasks").authenticated()
                    .requestMatchers("/users").hasRole("ADMIN")

                    .anyRequest().authenticated()
                }
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
                .build()
    }

    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}