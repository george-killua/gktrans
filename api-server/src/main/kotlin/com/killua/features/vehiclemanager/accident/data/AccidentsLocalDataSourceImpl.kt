package com.killua.features.vehiclemanager.accident.data

import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import java.util.*

class AccidentsLocalDataSourceImpl : AccidentsLocalDataSource {
    override suspend fun getAccident(accidentId: UUID): AccidentEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getCarAccidents(carId: UUID): List<AccidentEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAccidents(userId: UUID): List<AccidentEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun addAccident(carID: UUID): AccidentEntity {
        TODO("Not yet implemented")
    }
}