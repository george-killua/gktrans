package com.killua.features.company.domain.model

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto

data class CompanyDto(
    val id: String? = null,
    val name: String,
    val users: MutableList<UserDto> = mutableListOf(),
    val carsDto: MutableList<CarDto> = mutableListOf(),
    val accidentsDto: MutableList<AccidentDto> = mutableListOf(),
    val createdBy: UserDto? = null,
    val createdDate: Long? = null,
    val updatedBy: UserDto? = null, val updatedDate: Long? = null,
    val deletedBy: UserDto? = null, val deletedDate: Long? = null,
)

