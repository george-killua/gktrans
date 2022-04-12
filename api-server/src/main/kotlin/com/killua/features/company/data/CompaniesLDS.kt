package com.killua.features.company.data

import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import java.util.*

interface CompaniesLDS {
    suspend fun getCompany(companyId: UUID): CompanyEntity?
    suspend fun addCompany(company: CompanyDto, currentUser: UserEntity): CompanyEntity
     fun addCompanye(company: CompanyDto, currentUser: UserEntity): CompanyEntity
    suspend fun getUsers(companyId: UUID): List<UserEntity>
    suspend fun getCars(companyId: UUID): List<CarEntity>
    suspend fun getCompanyCarAccidents(companyId: UUID): List<AccidentEntity>
    suspend fun updateCompany(company: CompanyDto, currentUser: UserEntity)
    suspend fun removeCompany(companyId: UUID, currentUser: UserEntity)
    suspend fun undoRemoveCompany(companyId: UUID, currentUser: UserEntity)
    suspend fun cleanCompanyTable(): Int
}