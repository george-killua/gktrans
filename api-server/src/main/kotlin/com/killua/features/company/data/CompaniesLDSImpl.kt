package com.killua.features.company.data

import com.killua.extenstions.toUuid
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import org.joda.time.DateTime
import java.util.*

class CompaniesLDSImpl() : CompaniesLDS {

    override suspend fun getCompany(companyId: UUID): CompanyEntity? {
        return CompanyEntity[companyId]
    }

    override suspend fun addCompany(company: CompanyDto, currentUser: UserEntity): CompanyEntity {
        return CompanyEntity.new {
            name = company.name
            createdBy = currentUser
            createdDate = DateTime.now()
        }.apply {
            create(currentUser)
        }
    }

    override fun addCompanye(company: CompanyDto, currentUser: UserEntity): CompanyEntity {
        return CompanyEntity.new {
            name = company.name
            createdBy = currentUser
            createdDate = DateTime.now()
        }.apply {
            create(currentUser)
        }
    }

    override suspend fun getUsers(companyId: UUID): List<UserEntity> {
        return getCompany(companyId)?.users?.toList() ?: emptyList()
    }

    override suspend fun getCars(companyId: UUID): List<CarEntity> {
        return getCompany(companyId)?.cars?.toList() ?: emptyList()
    }

    override suspend fun getCompanyCarAccidents(companyId: UUID): List<AccidentEntity> {
        return getCompany(companyId)?.accidents?.toList() ?: emptyList()
    }

    override suspend fun updateCompany(company: CompanyDto, currentUser: UserEntity) {
        CompanyEntity[company.id?.toUuid()!!].apply {
            name = company.name
            update(currentUser)
        }
    }

    override suspend fun removeCompany(companyId: UUID, currentUser: UserEntity) {
        CompanyEntity[companyId].apply {
            softDelete(currentUser)
            users.run { forEach { _ -> softDelete(currentUser) } }
            cars.run { forEach { _ -> softDelete(currentUser) } }
            accidents.run { forEach { _ -> softDelete(currentUser) } }
        }
    }

    override suspend fun undoRemoveCompany(companyId: UUID, currentUser: UserEntity) {
        CompanyEntity[companyId].apply {
            deletedBy = null
            deletedDate = null
            users.run {
                forEach { _ ->
                    apply {
                        deletedBy = null
                        deletedDate = null
                    }
                }
            }
            cars.run {
                forEach { carEntity ->
                    carEntity.apply {
                        deletedBy = null
                        deletedDate = null
                        images.forEach { imageEntity ->
                            imageEntity.apply {
                                this.deletedBy = null
                                this.deletedDate = null
                            }
                        }
                        usedHistories.forEach { usedHistoryEntity ->
                            usedHistoryEntity.apply {
                                this.deletedBy = null
                                this.deletedDate = null
                                this.images.forEach { imageEntity ->
                                    imageEntity.apply {
                                        this.deletedBy = null
                                        this.deletedDate = null
                                    }
                                }
                            }
                        }
                    }
                }
            }
            accidents.forEach {

                it.apply {
                    deletedBy = null
                    deletedDate = null
                }
                it.accidentImage.forEach { imageEntity ->
                    imageEntity.apply {
                        this.deletedBy = null
                        this.deletedDate = null
                    }
                }
            }
        }
    }

    override suspend fun cleanCompanyTable(): Int {
        return CompanyEntity.all().sumOf { it.cleanSomeOfIt() }
    }

}