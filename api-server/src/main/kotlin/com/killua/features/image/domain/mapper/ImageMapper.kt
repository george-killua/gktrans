package com.killua.features.image.domain.mapper

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.domain.model.ImageDto

fun ImageEntity.toImageDto() =
    ImageDto(
        id = id.value.toString(),
        imageUrl = imageUri,
        createdBy = createdBy.fullName(),
        createdAt = createdDate.millis,
        updatedBy = updatedBy?.fullName(),
        updatedAt = updatedDate?.millis,
        deletedBy = deletedBy?.fullName(),
        deletedAt = updatedDate?.millis

    )
