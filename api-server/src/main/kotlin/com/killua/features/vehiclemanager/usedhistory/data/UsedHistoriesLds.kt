package com.killua.features.vehiclemanager.usedhistory.data

import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import java.util.*

interface UsedHistoriesLds {
    suspend fun getUsedHistory(usedHistorytId: UUID): UsedHistoryEntity?

}
