package com.killua.features.vehiclemanager.accident.domain.model

import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto

data class AccidentDto(
    val id: String,
    var drivers: List<UserDto>,
    val cars: List<CarDto>,
    val accidentImage: List<ImageDto>,
    var accidentDescription: String,
    var company: CompanyMentionDto,
)