package com.killua

import com.killua.config.AppConfig
import com.killua.di.applicationModule
import com.killua.di.imageModule
import com.killua.di.userModule
import com.killua.extenstions.DatabaseExt
import com.killua.plugins.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.server.engine.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@OptIn(KtorExperimentalLocationsAPI::class)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(
    koinModules: List<Module> = listOf(
        applicationModule,
        imageModule,
        userModule
    ),
) {
    install(Koin) {
        modules(koinModules)

    }
    setupConfiguration()
    val appConfig by inject<AppConfig>()
    DatabaseExt.initializeConnection(appConfig)
    configureSecurity()
    configureRouting()
    configureSockets()
    configureSerialization()
    configureTemplating()
    configureMonitoring()

    install(ShutDownUrl.ApplicationCallFeature) {
        // The URL that will be intercepted
        shutDownUrl = "/ktor/application/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }
}
