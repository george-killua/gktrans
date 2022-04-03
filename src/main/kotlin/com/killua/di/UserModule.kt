package com.killua.di

import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.UserLocalDataSourceImpl
import com.killua.features.user.domain.UserRepository
import com.killua.features.user.domain.UserRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    single { UserLocalDataSourceImpl() } bind UserLocalDataSource::class
    single { UserRepositoryImpl(get()) } bind UserRepository::class
}