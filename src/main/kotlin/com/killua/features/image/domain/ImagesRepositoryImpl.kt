package com.killua.features.image.domain

import com.killua.extenstions.toUuid
import com.killua.features.image.data.ImagesLocalDataSource
import com.killua.features.image.domain.mapper.toImageClass
import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.AccidentsLocalDataSource
import com.killua.features.vehiclemanager.car.data.CarsLocalDataSource
import com.killua.features.vehiclemanager.usedhistory.data.UsedHistoriesLocalDataSource

class ImagesRepositoryImpl(
    private val usersLds: UserLocalDataSource,
    private val imageLds: ImagesLocalDataSource,
    private val carLds: CarsLocalDataSource,
    private val accidentLds: AccidentsLocalDataSource,
    private val usedHistoryLds: UsedHistoriesLocalDataSource,
) : ImagesRepository {
    override suspend fun addUserImage(imagePath: String, userId: String, currentUser: UserEntity): ImageDto? {
        return usersLds.getUser(userId.toUuid())
            ?.let { imageLds.addUserImage(imagePath, it, currentUser).toImageClass() }
    }

    override suspend fun addAccidentImage(imagePath: String, accidentId: String, currentUser: UserEntity): ImageDto? {
        return accidentLds.getAccident(accidentId.toUuid())?.let {
            imageLds.addAccidentImage(imagePath, it, currentUser).toImageClass()
        }
    }

    override suspend fun addUsedHistoryImage(
        imagePath: String,
        usedHistoryId: String,
        currentUser: UserEntity,
    ): ImageDto? {
        return usedHistoryLds.getUsedHistory(usedHistoryId.toUuid())?.let {
            imageLds.addUsedHistoryImage(imagePath, it, currentUser).toImageClass()
        }
    }

    override suspend fun addCarImage(imagePath: String, carId: String, currentUser: UserEntity): ImageDto? {
        return carLds.getCar(carId.toUuid())?.let {
            imageLds.addCarImage(imagePath, it, currentUser).toImageClass()
        }
    }


    override suspend fun softDeleteImage(imageId: String, currentUser: UserEntity) {
        imageLds.softDeleteImage(imageId.toUuid(), currentUser)
    }

    override suspend fun cleanImageTable(): Int {
        return imageLds.cleanImageTable()
    }


}