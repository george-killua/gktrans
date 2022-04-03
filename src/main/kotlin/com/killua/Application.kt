package com.killua

import com.killua.config.AppConfig
import com.killua.di.applicationModule
import com.killua.extenstions.DatabaseExt
import io.ktor.application.*
import com.killua.plugins.*
import io.ktor.features.*
import io.ktor.locations.*
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(    koinModules: List<Module> = listOf(

    applicationModule
)) {
    install(Koin) {
        slf4jLogger(level = Level.ERROR)
        modules(koinModules)

    }
    setupConfiguration()
    val appConfig by inject<AppConfig>()

    DatabaseExt.initializeConnection(appConfig)
    configureRouting()
    configureSecurity()
    configureSockets()
    configureSerialization()
    configureTemplating()
    configureMonitoring()

}
