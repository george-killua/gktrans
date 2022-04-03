package com.killua.di

import com.killua.features.image.data.ImagesLocalDataSource
import com.killua.features.image.data.ImagesLocalDataSourceImpl
import com.killua.features.image.domain.ImagesRepository
import com.killua.features.image.domain.ImagesRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val imageModule = module {
    single { ImagesLocalDataSourceImpl() } bind ImagesLocalDataSource::class
    single { ImagesRepositoryImpl(get(),get(),get(),get()) } bind ImagesRepository::class
}