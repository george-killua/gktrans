package com.killua.features.company.domain

import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserMentionDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.domain.model.CarMentionDto

interface CompaniesRepo {
    suspend fun getCompany(companyId: String): CompanyMentionDto?
    suspend fun addCompany(company: CompanyDto, currentUser: UserEntity): CompanyMentionDto
    suspend fun getUsers(companyId: String): List<UserMentionDto>
    suspend fun getCars(companyId: String): List<CarMentionDto>
    suspend fun getAccident(companyId: String): List<AccidentEntity>
    suspend fun updateCompany(company: CompanyDto, currentUser: UserEntity)
    suspend fun removeCompany(companyId: String, currentUser: UserEntity)
    suspend fun undoRemoveCompany(companyId: String, currentUser: UserEntity)
    suspend fun cleanCompanyTable(): Int
}
