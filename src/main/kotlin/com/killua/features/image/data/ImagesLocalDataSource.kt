package com.killua.features.image.data

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity

interface ImagesLocalDataSource {
    fun addUserImage(imagePath: String, user: UserEntity, currentUser: UserEntity): ImageEntity
    fun addAccidentImage(imagePath: String, accident: AccidentEntity, currentUser: UserEntity): ImageEntity
    fun addUsedHistoryImage(
        imagePath: String,
        UsedHistoryEntity: UsedHistoryEntity,
        currentUser: UserEntity,
    ): ImageEntity

    fun addCarImage(imagePath: String, car: CarEntity, currentUser: UserEntity): ImageEntity
    fun softDeleteImage(image: ImageEntity, currentUser: UserEntity)

    fun cleanImageTable()

}