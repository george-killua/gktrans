package com.killua.features.vehiclemanager.car.data

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import java.util.*

class CarsLdsImpl : CarsLds {
    override suspend fun addCar(carDto: CarDto): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyCars(owner: UserDto): List<CarEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getCar(carId: UUID): CarEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun updateCar(carDto: CarDto): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun addDriver(user: UserDto, carId: String): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun updateDriver(driver: UserDto): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getCarsUsedByUser(owner: UserDto): List<CarEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccidentsOfCar(carId: UUID): List<AccidentEntity> {
        TODO("Not yet implemented")
    }


}