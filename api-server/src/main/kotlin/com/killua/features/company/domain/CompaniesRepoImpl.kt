package com.killua.features.company.domain

import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.extenstions.toUuid
import com.killua.features.company.data.CompaniesLDSImpl
import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.company.validation.InputValidator
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.mapper.toUserMentionDto
import com.killua.features.user.domain.model.UserMentionDto
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.domain.mapper.toCarMentionDto
import com.killua.features.vehiclemanager.car.domain.model.CarMentionDto
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class CompaniesRepoImpl(private val companyLDS: CompaniesLDSImpl) : CompaniesRepo, KoinComponent {
    private val inputValidator: InputValidator by inject()
}
  /*  override suspend fun getCompany(companyId: String): CompanyMentionDto? = dbTransaction(
        {
            companyLDS.getCompany(companyId.toUuid())?.toCompanyMentionDto()
        }, Dispatchers.IO
    )

    override suspend fun addCompany(company: CompanyDto, currentUser: UserEntity): CompanyMentionDto = dbTransaction(
        {
            companyLDS.addCompany(company, currentUser).toCompanyMentionDto()
        }, Dispatchers.IO
    )

    override suspend fun getUsers(companyId: String): List<UserMentionDto> = dbTransaction(
        {
            companyLDS.getUsers(companyId.toUuid()).map { it.toUserMentionDto() }
        }, Dispatchers.IO
    )

    override suspend fun getCars(companyId: String): List<CarMentionDto> = dbTransaction(
        {
            companyLDS.getCars(companyId.toUuid()).map { it.toCarMentionDto() }
        }, Dispatchers.IO
    )

    override suspend fun getAccident(companyId: String): List<AccidentEntity> = dbTransaction(
        {
            companyLDS.getCompanyCarAccidents(companyId.toUuid())
        }, Dispatchers.IO
    )

    override suspend fun updateCompany(company: CompanyDto, currentUser: UserEntity) {
        inputValidator.newInstance
            .isUUIDValid(company.id)
            .isStringLengthValid(company.name, "company name", 16)
            .validate()

        dbTransaction(
            {
                companyLDS.updateCompany(company, currentUser)
            }, Dispatchers.IO
        )
    }

    override suspend fun removeCompany(companyId: String, currentUser: UserEntity) = dbTransaction(
        {
            companyLDS.removeCompany(companyId.toUuid(), currentUser)
        }, Dispatchers.IO
    )

    override suspend fun undoRemoveCompany(companyId: String, currentUser: UserEntity) = dbTransaction(
        {
            companyLDS.undoRemoveCompany(companyId.toUuid(), currentUser)
        }, Dispatchers.IO
    )

    override suspend fun cleanCompanyTable(): Int =
        dbTransaction(
            {
                companyLDS.cleanCompanyTable()
            }, Dispatchers.IO
        )
}*/