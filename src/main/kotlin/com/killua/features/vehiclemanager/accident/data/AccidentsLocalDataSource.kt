package com.killua.features.vehiclemanager.accident.data

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import java.util.*

interface AccidentsLocalDataSource {
    suspend fun getAccident(accidentId: UUID): AccidentEntity?

}
