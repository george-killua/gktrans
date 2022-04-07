package com.killua.features.vehiclemanager.accident.data

import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import java.util.*
import kotlin.collections.List

interface AccidentsLocalDataSource {
    suspend fun getAccident(accidentId: UUID): AccidentEntity?
    suspend fun getCarAccidents(carId:UUID): List<AccidentEntity>
    suspend fun getUserAccidents(userId:UUID): List<AccidentEntity>
    suspend fun addAccident(carID:UUID): AccidentEntity
}
