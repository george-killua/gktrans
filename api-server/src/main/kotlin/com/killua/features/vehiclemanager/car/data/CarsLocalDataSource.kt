package com.killua.features.vehiclemanager.car.data

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import java.util.UUID

interface CarsLocalDataSource {

    suspend fun addCar(carDto: CarDto): CarEntity
    suspend fun getCarsWithOwner(owner: UserDto): List<CarEntity>
    fun getCar(carId: UUID): CarEntity?
    fun updateCar(carDto: CarDto): CarEntity
    fun updateDriver(driver: UserDto): CarEntity
    suspend fun getCarsUsedByUser(owner: UserDto): List<CarEntity>
    fun getAccidentsOfCar(carDto: CarDto): List<AccidentEntity>
    fun getAccident(userId: String, accidentId: String): AccidentEntity
    suspend fun addDriver(user: UserDto, carId: String): CarEntity
}