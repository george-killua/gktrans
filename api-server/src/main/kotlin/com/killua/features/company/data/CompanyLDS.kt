package com.killua.features.company.data

import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import java.util.*

interface CompanyLDS {
    fun addCompany(company: CompanyDto, currentUser: UserEntity)
    fun getUsers(companyId: UUID): List<UserDto>
    fun getCars(companyId: UUID): List<CarDto>
    fun updateCompany(company: CompanyDto, currentUser: UserEntity)
    fun removeCompany(companyId: UUID, currentUser: UserEntity)
    fun undoRemoveCompany(companyId: UUID, currentUser: UserEntity)
    fun cleanCompanyTable(): Int
}