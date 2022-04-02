package com.killua.features.image.data

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity

class ImagesLocalDataSourceImpl : ImagesLocalDataSource {
    override fun addUserImage(imagePath: String, user: UserEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.user = user
            create(currentUser)
        }
    }

    override fun addAccidentImage(imagePath: String, accident: AccidentEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.accident = accident
            create(currentUser)
        }
    }

    override fun addUsedHistoryImage(imagePath: String, UsedHistoryEntity: UsedHistoryEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.usedHistory = usedHistory
            create(currentUser)
        }
    }

    override fun addCarImage(imagePath: String, car: CarEntity, currentUser: UserEntity): ImageEntity {
        return ImageEntity.new {
            this.imageUri = imagePath
            this.car = car
            create(currentUser)
        }
    }

    override fun softDeleteImage(image: ImageEntity, currentUser: UserEntity) {
        image.softDelete(currentUser)
    }

    override fun cleanImageTable() {
        ImageEntity.all().map { it.delete() }
    }

}