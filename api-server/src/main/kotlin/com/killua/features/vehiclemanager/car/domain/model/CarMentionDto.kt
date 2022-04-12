package com.killua.features.vehiclemanager.car.domain.model

import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.domain.model.UserDto

@kotlinx.serialization.Serializable
data class CarMentionDto(
    val id: String? = null,
    val owner: UserDto? = null,
    val vehicleRegistration: String? = null,
    val vehicleMake: String? = null,
    val vehicleType: String? = null,
    val fuelType: FuelType? = FuelType.DIESEL,
    val currentDriver: UserDto? = null,
    val images: List<ImageDto> = emptyList(),
)