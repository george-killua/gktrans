package com.killua.features.company.domain.model

import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto

data class CompanyDto(
    val id: String, val name: String,
    val users: MutableList<UserDto> = mutableListOf(),
    val carsDto: MutableList<CarDto> = mutableListOf(),
    val accidentsDto: MutableList<AccidentDto> = mutableListOf(),
    val createdBy: UserDto,
    val createdDate: Long,
    val updatedBy: UserDto? = null, val updatedDate: Long? = null,
    val deletedBy: UserDto? = null, val deletedDate: Long? = null,
)

