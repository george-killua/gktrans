package com.killua.features.vehiclemanager.car.domain.model

import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.image.domain.model.ImageDto
import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.info.domain.model.CarInsuranceDto
import com.killua.features.vehiclemanager.usedhistory.domain.model.UsedHistoryDto

data class CarDto(
    val id: String? = null,
    val owner: UserDto? = null,
    var company: CompanyMentionDto? = null,
    val carInsuranceInfo: CarInsuranceDto? = null,
    val vehicleRegistration: String? = null,
    val vehicleMake: String? = null,
    val vehicleType: String? = null,
    val fuelType: FuelType? = FuelType.DIESEL,
    val yearOfManufacture: String? = null,
    val carStickerValidUntil: Long? = null,
    val currentDriver: UserDto? = null,
    val usedHistories: List<UsedHistoryDto> = emptyList(),
    val accidents: List<AccidentDto> = emptyList(),
    val images: List<ImageDto> = emptyList()
    )
