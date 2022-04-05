package com.killua.features.vehiclemanager.car.data

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import java.util.*

class CarsLocalDataSourceImpl : CarsLocalDataSource {
    override suspend fun addCar(carDto: CarDto): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getCarsWithOwner(owner: UserDto): List<CarEntity> {
        TODO("Not yet implemented")
    }

    override fun getCar(carId: UUID): CarEntity? {
        TODO("Not yet implemented")
    }

    override fun updateCar(carDto: CarDto): CarEntity {
        TODO("Not yet implemented")
    }

    override fun updateDriver(driver: UserDto): CarEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getCarsUsedByUser(owner: UserDto): List<CarEntity> {
        TODO("Not yet implemented")
    }

    override fun getAccidentsOfCar(carDto: CarDto): List<AccidentEntity> {
        TODO("Not yet implemented")
    }

    override fun getAccident(userId: String, accidentId: String): AccidentEntity {
        TODO("Not yet implemented")
    }

    override suspend fun addDriver(user: UserDto, carId: String): CarEntity {
        TODO("Not yet implemented")
    }
}