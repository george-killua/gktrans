package com.killua.features.image.domain.mapper

import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.domain.model.ImageDto

fun ImageEntity.toImageDto() =
    ImageDto(
        id = id.value.toString(),
        imageUrl = imageUri,
        createdBy = createdBy.id.value.toString(),
        createdAt = createdDate.millis,
        updatedBy = updatedBy?.id?.value.toString(),
        updatedAt = updatedDate?.millis,
        deletedBy = deletedBy?.id?.value.toString(),
        deletedAt = updatedDate?.millis
    )
