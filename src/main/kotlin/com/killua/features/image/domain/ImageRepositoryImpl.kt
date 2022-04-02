package com.killua.features.image.domain

import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.image.data.ImagesLocalDataSource
import com.killua.features.image.data.dao.ImagesEntity
import com.killua.features.image.domain.mapper.toImageClass
import com.killua.features.image.domain.model.ImageDto
import java.util.*

class ImageRepositoryImpl(private val imagesLds: ImagesLocalDataSource) : ImageRepository {
    override suspend fun addImage(image: ImageDto): ImagesEntity {
        return dbTransaction {
            imagesLds.addImage(image)
        }
    }

    override suspend fun deleteImage(imageId: String): Boolean {
        return dbTransaction {
            imagesLds.deleteImage(imageId)
        }
    }
    override suspend fun getAllImages(): List<ImageDto> {
        return dbTransaction {
            ImagesEntity.all().map { it.toImageClass() }
        }
    }
    override suspend fun getImage(id: String): ImageDto {
        return dbTransaction {
            ImagesEntity[UUID.fromString(id)].toImageClass()
        }
    }
}