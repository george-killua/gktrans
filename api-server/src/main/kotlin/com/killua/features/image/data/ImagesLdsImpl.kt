package com.killua.features.image.data

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import java.util.*

class ImagesLdsImpl : ImagesLds {
    override suspend fun addUserImage(imagePath: String, user: UserEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.user = user
            create(currentUser)
        }
    }

    override suspend fun addAccidentImage(
        imagePath: String,
        accident: AccidentEntity,
        currentUser: UserEntity,
    ): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.accident = accident
            create(currentUser)
        }
    }

    override suspend fun addUsedHistoryImage(
        imagePath: String,
        UsedHistoryEntity: UsedHistoryEntity,
        currentUser: UserEntity,
    ): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.usedHistory = usedHistory
            create(currentUser)
        }
    }

    override suspend fun addCarImage(imagePath: String, car: CarEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.car = car
            create(currentUser)
        }
    }

    override suspend fun softDeleteImage(imageId: UUID, currentUser: UserEntity) {
        ImageEntity.findById(imageId)?.softDelete(currentUser)
    }

    override suspend fun cleanImageTable(): Int {
        return ImageEntity.all().sumOf { it.cleanSomeOfIt() }
    }

}