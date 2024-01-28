package com.soavedev.seeddesafiobasecamp.config.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

@Configuration
@Profile("local")
class DataBaseConfigLocal {

    @Value("\${spring.datasource.driver-class-name}")
    private lateinit var className: String

    @Value("\${spring.datasource.url}")
    private lateinit var jdbcUrl: String

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String


    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource(dataSourceConfig)
    }

    private val dataSourceConfig: HikariConfig
        get() {
            logger.info { "Creating H2 Local database connection..." }
            val config = HikariConfig()
            config.driverClassName = className
            config.jdbcUrl = jdbcUrl
            config.username = username
            config.password = password
            return config
        }
}