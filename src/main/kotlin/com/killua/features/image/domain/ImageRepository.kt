package com.killua.features.image.domain

import com.killua.features.image.data.dao.ImagesEntity
import com.killua.features.image.domain.model.ImageDto

interface ImageRepository {
    suspend fun addImage(image: ImageDto): ImagesEntity
    suspend fun deleteImage(imageId: String): Boolean
    suspend fun getImage(id: String): ImageDto
    suspend fun getAllImages(): List<ImageDto>
}