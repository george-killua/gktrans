package com.killua.di

import com.killua.config.AppConfig
import com.killua.features.socket.SocketServer

import org.koin.dsl.module
import com.killua.logger.ApiLogger


val applicationModule = module {
    single { AppConfig() }
    single { ApiLogger() }
    single { SocketServer(get()) }

}