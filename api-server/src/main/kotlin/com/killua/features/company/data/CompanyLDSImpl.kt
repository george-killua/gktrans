package com.killua.features.company.data

import com.killua.extenstions.toUuid
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.model.UserDto
import com.killua.features.vehiclemanager.accident.data.AccidentsLocalDataSource
import com.killua.features.vehiclemanager.car.domain.mapper.toCarDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto
import org.joda.time.DateTime
import java.util.*

class CompanyLDSImpl(val accidentsLds: AccidentsLocalDataSource) : CompanyLDS {
    override fun addCompany(company: CompanyDto, currentUser: UserEntity) {
        CompanyEntity.new {
            name = company.name
            createdBy = currentUser
            createdDate = DateTime.now()
        }.apply {
            create(currentUser)
        }
    }

    override fun getUsers(companyId: UUID): List<UserDto> {
        return CompanyEntity[companyId].users.map { it.toUserDto() }.toMutableList()
    }

    override fun getCars(companyId: UUID): List<CarDto> {
        return CompanyEntity[companyId].cars.map { it.toCarDto() }
    }

    override fun updateCompany(company: CompanyDto, currentUser: UserEntity) {
        CompanyEntity[company.id.toUuid()].apply {
            name = company.name
            update(currentUser)
        }
    }

    override fun removeCompany(companyId: UUID, currentUser: UserEntity) {
        CompanyEntity[companyId].apply {
            softDelete(currentUser)
            users.run { forEach { _ -> softDelete(currentUser) } }
            cars.run { forEach { _ -> softDelete(currentUser) } }
        }
    }

    override fun undoRemoveCompany(companyId: UUID, currentUser: UserEntity) {
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

    override fun cleanCompanyTable(): Int {
        return CompanyEntity.all().sumOf { it.cleanSomeOfIt() }
    }

}