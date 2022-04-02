package com.killua.di

import com.killua.config.AppConfig

import org.koin.dsl.bind
import org.koin.dsl.module


val applicationModule = module {
    single { AppConfig() }
}