package com.killua.di

import com.killua.config.AppConfig
import com.killua.features.company.validation.InputValidator
import com.killua.features.company.validation.InputValidatorImpl
import com.killua.features.socket.SocketServer
import com.killua.logger.ApiLogger
import org.koin.dsl.bind
import org.koin.dsl.module


val applicationModule = module {
    single { AppConfig() }
    single { ApiLogger() }
    single { SocketServer(get()) }
    single { InputValidatorImpl() } bind InputValidator::class
}