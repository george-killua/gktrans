package com.killua.di

import com.killua.features.company.data.CompanyLDS
import com.killua.features.company.data.CompanyLDSImpl
import com.killua.features.company.domain.CompanyRepository
import com.killua.features.company.domain.CompanyRepositoryImpl
import com.killua.features.image.data.ImagesLocalDataSource
import com.killua.features.image.data.ImagesLocalDataSourceImpl
import com.killua.features.image.domain.ImagesRepository
import com.killua.features.image.domain.ImagesRepositoryImpl
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.UserLocalDataSourceImpl
import com.killua.features.user.domain.UserRepository
import com.killua.features.user.domain.UserRepositoryImpl
import com.killua.features.vehiclemanager.accident.data.AccidentsLocalDataSource
import com.killua.features.vehiclemanager.accident.data.AccidentsLocalDataSourceImpl
import com.killua.features.vehiclemanager.car.data.CarsLocalDataSource
import com.killua.features.vehiclemanager.car.data.CarsLocalDataSourceImpl
import com.killua.features.vehiclemanager.usedhistory.data.UsedHistoriesLocalDataSource
import com.killua.features.vehiclemanager.usedhistory.data.UsedHistoriesLocalDataSourceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresModule = module {
    //userModule
    single { UserLocalDataSourceImpl() } bind UserLocalDataSource::class
    single { UserRepositoryImpl(get()) } bind UserRepository::class
    //companyModule
    single { CompanyLDSImpl(get()) } bind CompanyLDS::class
    single { CompanyRepositoryImpl(get()) } bind CompanyRepository::class

    //imageModule
    single { ImagesLocalDataSourceImpl() } bind ImagesLocalDataSource::class
    single { ImagesRepositoryImpl(get(), get(), get(), get(), get()) } bind ImagesRepository::class
    //accidentModule
    single { AccidentsLocalDataSourceImpl() } bind AccidentsLocalDataSource::class
    single { UsedHistoriesLocalDataSourceImpl() } bind UsedHistoriesLocalDataSource::class
    single { CarsLocalDataSourceImpl() } bind CarsLocalDataSource::class
}