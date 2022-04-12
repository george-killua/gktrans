package com.killua.features.vehiclemanager.usedhistory.domain.model

import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto

@kotlinx.serialization.Serializable
data class UsedHistoryDto(
    var user: UserDto,
    var car: CarDto,
    val images: List<ImageDto>,
    var givingDate: Long,
    val returnDate: Long?,
)

