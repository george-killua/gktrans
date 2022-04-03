package com.killua.features.image.domain.mapper

import com.killua.features.image.data.dao.ImagesEntity
import com.killua.features.image.domain.model.ImageDto

fun ImagesEntity.toImageClass() = ImageDto(id.value.toString(), null, imageUri, takenDate)

