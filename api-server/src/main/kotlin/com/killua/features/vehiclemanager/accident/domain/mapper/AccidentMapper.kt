package com.killua.features.vehiclemanager.accident.domain.mapper

import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.image.domain.mapper.toImageDto
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.domain.mapper.toCarDto

fun AccidentEntity.toAccidentDto() = AccidentDto(
    id = id.value.toString(),
    drivers = drivers.map { it.toUserDto() },
    cars = cars.map { it.toCarDto() },
    accidentImage = accidentImage.map { it.toImageDto() },
    accidentDescription = accidentDescription ?: "",
    company = company.toCompanyMentionDto(),
)
