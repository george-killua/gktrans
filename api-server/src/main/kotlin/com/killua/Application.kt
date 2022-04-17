package com.killua

import com.killua.config.AppConfig
import com.killua.di.FeaturesModule
import com.killua.di.applicationModule
import com.killua.extenstions.DatabaseExt
import com.killua.plugins.*
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.Koin


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(
    koinModules: List<Module> = listOf(
        applicationModule,
        FeaturesModule.accidentModule,
        FeaturesModule.carModule,
        FeaturesModule.companyModule,
        FeaturesModule.imageModule,
        FeaturesModule.userModule

    ),
) {
    install(KoinPlugin) {
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


}
