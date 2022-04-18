package com.killua.features.vehiclemanager.car.data.dao

import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.domain.model.FuelType
import com.killua.features.vehiclemanager.car.info.dao.data.CarInsuranceEntity
import com.killua.features.vehiclemanager.car.info.dao.data.CarInsuranceTable
import com.killua.features.commondao.CarsAccidentsTable
import com.killua.features.commondao.CommonEntity
import com.killua.features.commondao.CommonTable
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime
import java.util.*

class CarEntity(id: EntityID<UUID>) : CommonEntity(id, CarTable) {
    companion object : UUIDEntityClass<CarEntity>(CarTable)

    var owner by UserEntity optionalReferencedOn CarTable.owner
    var company by CompanyEntity optionalReferencedOn CarTable.company
    val carInsuranceInfo by CarInsuranceEntity referencedOn CarTable.carInsuranceInfo
    var vehicleRegistration by CarTable.vehicleRegistration
    var vehicleMake by CarTable.vehicleMake
    var vehicleType by CarTable.vehicleType
    var fuelType by CarTable.fuelType
    var yearOfManufacture by CarTable.yearOfManufacture
    var carStickerValidUntil by CarTable.carStickerValidUntil
    var currentDriver by UserEntity optionalReferencedOn CarTable.currentDriver
    val accidents by AccidentEntity referrersOn CarsAccidentsTable.accident
    val usedHistories by UsedHistoryEntity referrersOn UsedHistoryTable.car
    val images by ImageEntity optionalReferrersOn ImagesTable.car
}

object CarTable : CommonTable("cars") {
    val owner = reference("owner", UserTable).nullable()
    val vehicleMake = varchar("vehicle_make", 15)
    val carInsuranceInfo = reference("car_insurance_info", CarInsuranceTable)
    val company = reference("company", CompanyTable).nullable()
    val vehicleType = varchar("vehicle_type", 15)
    val fuelType = enumerationByName("fuel_type", 9, FuelType::class).nullable().default(null)
    val yearOfManufacture = varchar("year_of_manufacture", 15).nullable().default(null)
    val carStickerValidUntil = date("car_sticker_valid_until").nullable().default(DateTime.now())
    val vehicleRegistration = CarTable.varchar("vehicle_registration", 15).default("")
    val currentDriver = reference("actual_driver", UserTable, null, null).nullable()
}

