package com.killua.features.vehiclemanager.accident.domain.model

import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.domain.model.UserMentionDto
import com.killua.features.vehiclemanager.car.domain.model.CarMentionDto

@kotlinx.serialization.Serializable
data class AccidentDto(
    val id: String? = null,
    var drivers: List<UserMentionDto> = emptyList(),
    val cars: List<CarMentionDto> = emptyList(),
    val accidentImage: List<ImageDto> = emptyList(),
    var accidentDescription: String = "",
    var company: CompanyMentionDto,
)