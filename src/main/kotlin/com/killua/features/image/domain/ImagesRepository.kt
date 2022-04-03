package com.killua.features.image.domain

import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.data.dao.UserEntity
import java.util.*

interface ImagesRepository {
    suspend fun addUserImage(imagePath: String, userId: String, currentUser: UserEntity): ImageDto?
    suspend fun addAccidentImage(imagePath: String, accidentId: UUID, currentUser: UserEntity): ImageDto
    suspend fun addUsedHistoryImage(
        imagePath: String,
        usedHistoryId: String,
        currentUser: UserEntity,
    ): ImageDto

    suspend fun addCarImage(imagePath: String, carId: UUID, currentUser: UserEntity): ImageDto
    suspend fun softDeleteImage(imageId: UUID, currentUser: UserEntity)

    suspend fun cleanImageTable()

}