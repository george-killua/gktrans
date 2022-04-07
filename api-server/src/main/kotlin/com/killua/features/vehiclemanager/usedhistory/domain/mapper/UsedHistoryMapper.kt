package com.killua.features.vehiclemanager.usedhistory.domain.mapper

import com.killua.features.image.domain.mapper.toImageDto
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.vehiclemanager.car.domain.mapper.toCarDto
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import com.killua.features.vehiclemanager.usedhistory.domain.model.UsedHistoryDto

fun UsedHistoryEntity.toUsedHistoryDto() = UsedHistoryDto(
    user = user.toUserDto(),
    car = car.toCarDto(),
    images = images.map { it.toImageDto() },
    givingDate = givingDate.millis,
    returnDate = returnDate?.millis
)
