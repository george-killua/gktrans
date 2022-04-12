package com.killua.features.vehiclemanager.accident.data

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import java.util.*

interface AccidentsLds {
    suspend fun addAccident(accidentDto: AccidentDto): AccidentEntity
    suspend fun getAccident(accidentId: UUID): AccidentEntity?
    suspend fun getCarAccidents(carId: UUID): List<AccidentEntity>
    suspend fun getAccidentCars(accidentId: UUID): List<CarEntity>
    suspend fun getUserAccidents(userId: UUID): List<AccidentEntity>
    suspend fun getAccidentUsers(accidentId: UUID): List<UserEntity>
    suspend fun deleteAccident(accidentId: UUID, currentUser: UserEntity): Boolean
    suspend fun cleanAccidentTable(): Int
}
