package com.killua.di

import com.killua.features.company.data.CompaniesLDS
import com.killua.features.company.data.CompaniesLDSImpl
import com.killua.features.company.domain.CompaniesRepo
import com.killua.features.company.domain.CompaniesRepoImpl
import com.killua.features.image.data.ImagesLds
import com.killua.features.image.data.ImagesLdsImpl
import com.killua.features.image.domain.ImagesRepo
import com.killua.features.image.domain.ImagesRepoImpl
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.UserLocalDataSourceImpl
import com.killua.features.user.domain.UserRepoImpl
import com.killua.features.user.domain.UsersRepo
import com.killua.features.vehiclemanager.accident.data.AccidentsLdsImpl
import com.killua.features.vehiclemanager.accident.data.AccidentsLds
import com.killua.features.vehiclemanager.car.data.CarsLds
import com.killua.features.vehiclemanager.car.data.CarsLdsImpl
import com.killua.features.vehiclemanager.usedhistory.data.UsedHistoriesLds
import com.killua.features.vehiclemanager.usedhistory.data.UsedHistoriesLdsImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresModule = module {
    //userModule
    single { UserLocalDataSourceImpl() } bind UserLocalDataSource::class
    single { UserRepoImpl(get(),Dispatchers.IO) } bind UsersRepo::class
    //companyModule
    single { CompaniesLDSImpl() } bind CompaniesLDS::class
  //  single { CompaniesRepoImpl(get()) } bind CompaniesRepo::class

    //imageModule
    single { ImagesLdsImpl() } bind ImagesLds::class
    single { ImagesRepoImpl(get(), get(), get(), get(), get()) } bind ImagesRepo::class
    //accidentModule
    single { AccidentsLdsImpl() } bind AccidentsLds::class
    single { UsedHistoriesLdsImpl() } bind UsedHistoriesLds::class
    //car
    single { CarsLdsImpl() } bind CarsLds::class
}