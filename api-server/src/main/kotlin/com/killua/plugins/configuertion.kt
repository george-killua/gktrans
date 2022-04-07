package com.killua.plugins

import com.killua.config.AppConfig
import com.killua.config.DatabaseConfig
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import org.koin.ktor.ext.inject

fun Application.setupConfiguration() {
    val appConfiguration by inject<AppConfig>()
    val appConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
    //database configuration
    val dbConfig = appConfig.config("ktor.database")
    val jdbcUrl = dbConfig.property("DatabaseUrl").getString()
    val username = dbConfig.property("DatabaseUser").getString()
    val password = dbConfig.property("DatabasePassword").getString()
    val driver = dbConfig.property("DatabaseDriver").getString()
    appConfiguration.databaseConfig = DatabaseConfig(jdbcUrl, username, password, driver)
}