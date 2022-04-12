package com.killua.features.vehiclemanager.accident.data

import com.killua.extenstions.toUuid
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.domain.model.AccidentDto
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import org.jetbrains.exposed.sql.SizedCollection
import java.util.*

class AccidentsLdsImpl(

) : AccidentsLds {
    override suspend fun addAccident(accidentDto: AccidentDto): AccidentEntity {
      /*  val companyEntity: CompanyEntity? = companiesLDS.getCompany(accidentDto.company.id.toUuid())
        val cars: SizedCollection<CarEntity> =
            SizedCollection(accidentDto.cars.mapNotNull { it.id?.let { it1 -> carLDS.getCar(it1.toUuid()) } })
        val users = accidentDto.drivers.mapNotNull { it.id?.let { it1 -> userLDS.getUser(it1.toUuid()) } }
        return AccidentEntity.new {
            this.accidentDescription = accidentDto.accidentDescription
            this.cars = cars
            this.drivers = SizedCollection(users.toMutableList())
            if (companyEntity != null) {
                this.company = companyEntity
            }
        }*/
        TODO()
    }

    override suspend fun getAccident(accidentId: UUID): AccidentEntity? {
        return AccidentEntity.get(accidentId)
    }

    override suspend fun getCarAccidents(carId: UUID): List<AccidentEntity> {
      TODO()
        //return carLDS.getAccidentsOfCar(carId)
    }

    override suspend fun getAccidentCars(accidentId: UUID): List<CarEntity> {
        return getAccident(accidentId)?.cars?.toList() ?: emptyList()
    }

    override suspend fun getUserAccidents(userId: UUID): List<AccidentEntity> {
        return emptyList()
        //userLDS.getUser(userId)?.accidents?.toList() ?:
    }

    override suspend fun getAccidentUsers(accidentId: UUID): List<UserEntity> {
        return getAccident(accidentId)?.drivers?.toList() ?: emptyList()
    }


    override suspend fun deleteAccident(accidentId: UUID, currentUser: UserEntity): Boolean {
        val accident = getAccident(accidentId)
        val accidentCompany = accident?.company?.id?.value
        getAccidentCars(accidentId).mapNotNull { it.company }.filter { it.id.value != accidentCompany }.ifEmpty {
            accident?.softDelete(currentUser)
            return true
        }
        return false
    }

    override suspend fun cleanAccidentTable(): Int {
        return AccidentEntity.all().sumOf { it.cleanSomeOfIt() }
    }
}