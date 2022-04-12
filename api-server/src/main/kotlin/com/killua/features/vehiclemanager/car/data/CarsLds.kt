package com.killua.features.vehiclemanager.car.data

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import java.util.*

interface CarsLds {

    suspend fun addCar(carDto: CarDto): CarEntity
    suspend fun getCompanyCars(owner: UserDto): List<CarEntity>
    suspend fun getCar(carId: UUID): CarEntity?
    suspend fun updateCar(carDto: CarDto): CarEntity
    suspend fun addDriver(user: UserDto, carId: String): CarEntity
    suspend fun updateDriver(driver: UserDto): CarEntity
    suspend fun getCarsUsedByUser(owner: UserDto): List<CarEntity>
    suspend fun getAccidentsOfCar(carId: UUID): List<AccidentEntity>
}