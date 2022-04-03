package com.killua.features.image.domain

import com.killua.extenstions.toUuid
import com.killua.features.image.data.ImagesLocalDataSource
import com.killua.features.image.domain.mapper.toImageClass
import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.AccidentsLocalDataSource
import com.killua.features.vehiclemanager.car.data.CarsLocalDataSource
import java.util.*

class ImagesRepositoryImpl(
    private val usersLds: UserLocalDataSource,
    private val imageLds: ImagesLocalDataSource,
    private val carLds: CarsLocalDataSource,
    private val accidentLds: AccidentsLocalDataSource,
) : ImagesRepository {
    override suspend fun addUserImage(imagePath: String, userId: String, currentUser: UserEntity): ImageDto? {
        return usersLds.getUser(userId.toUuid())
            ?.let { imageLds.addUserImage(imagePath, it, currentUser).toImageClass() }
    }

    override suspend fun addAccidentImage(imagePath: String, accidentId: UUID, currentUser: UserEntity): ImageDto {
        TODO("Not yet implemented")
    }

    override suspend fun addUsedHistoryImage(
        imagePath: String,
        usedHistoryId: String,
        currentUser: UserEntity,
    ): ImageDto {
        TODO("Not yet implemented")
    }

    override suspend fun addCarImage(imagePath: String, carId: UUID, currentUser: UserEntity): ImageDto {
        TODO("Not yet implemented")
    }


    override suspend fun softDeleteImage(imageId: UUID, currentUser: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun cleanImageTable() {
        TODO("Not yet implemented")
    }


}