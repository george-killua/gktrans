package com.killua.features.vehiclemanager.usedhistory.data

import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import java.util.*

class UsedHistoriesLocalDataSourceImpl : UsedHistoriesLocalDataSource {
    override suspend fun getUsedHistory(usedHistorytId: UUID): UsedHistoryEntity? {
        TODO("Not yet implemented")
    }
}