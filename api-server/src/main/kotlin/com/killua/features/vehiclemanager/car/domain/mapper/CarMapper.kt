package com.killua.features.vehiclemanager.car.domain.mapper

import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.image.domain.mapper.toImageDto
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.vehiclemanager.accident.domain.mapper.toAccidentDto
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import com.killua.features.vehiclemanager.car.info.domain.mapper.toCarInfoDto
import com.killua.features.vehiclemanager.usedhistory.domain.mapper.toUsedHistoryDto


fun CarEntity.toCarDto(): CarDto {
    return CarDto(
        id = id.value.toString(),
        owner = owner?.toUserDto(),
        company = company?.toCompanyMentionDto(),
        carInsuranceInfo = carInsuranceInfo.toCarInfoDto(),
        vehicleRegistration = vehicleRegistration,
        vehicleMake = vehicleMake,
        vehicleType = vehicleType,
        fuelType = fuelType,
        yearOfManufacture = yearOfManufacture,
        carStickerValidUntil = carStickerValidUntil?.millis,
        currentDriver = currentDriver?.toUserDto(),
        accidents = accidents.map { it.toAccidentDto() },
        usedHistories = usedHistories.map { it.toUsedHistoryDto() },
        images = images.map { it.toImageDto() }

    )
}