package com.killua.features.image.data

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import java.util.UUID

interface ImagesLocalDataSource {
  suspend  fun addUserImage(imagePath: String, user: UserEntity, currentUser: UserEntity): ImageEntity
  suspend  fun addAccidentImage(imagePath: String, accident: AccidentEntity, currentUser: UserEntity): ImageEntity
  suspend  fun addUsedHistoryImage(
        imagePath: String,
        UsedHistoryEntity: UsedHistoryEntity,
        currentUser: UserEntity,
    ): ImageEntity

   suspend fun addCarImage(imagePath: String, car: CarEntity, currentUser: UserEntity): ImageEntity
   suspend fun softDeleteImage(imageId: UUID, currentUser: UserEntity)

   suspend fun cleanImageTable()

}