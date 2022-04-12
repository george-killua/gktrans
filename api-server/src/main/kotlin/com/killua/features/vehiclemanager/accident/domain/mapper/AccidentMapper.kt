package com.killua.features.vehiclemanager.accident.domain.mapper

import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.image.domain.mapper.toImageDto
import com.killua.features.user.domain.mapper.toUserMentionDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.domain.mapper.toCarMentionDto

fun AccidentEntity.toAccidentDto() = AccidentDto(
    id = id.value.toString(),
    drivers = drivers.map { it.toUserMentionDto() },
    cars = cars.map { it.toCarMentionDto() },
    accidentImage = accidentImage.map { it.toImageDto() },
    accidentDescription = accidentDescription ?: "",
    company = company.toCompanyMentionDto(),
)
