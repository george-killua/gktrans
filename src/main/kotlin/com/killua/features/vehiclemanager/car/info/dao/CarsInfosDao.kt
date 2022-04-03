package com.killua.features.vehiclemanager.car.info.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.commondao.CarsInfosTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.date
import java.util.*

class CarInsuranceInfosEntity(id: EntityID<UUID>) : CommonEntity(id, CarInsuranceInfosTable) {
    companion object : UUIDEntityClass<CarInsuranceInfosEntity>(CarInsuranceInfosTable)

    val cars by CarEntity via CarsInfosTable
    var policyholderName by CarInsuranceInfosTable.policyholderName
    var policyholderAddress by CarInsuranceInfosTable.policyholderAddress
    var policyholderNumber by CarInsuranceInfosTable.policyholderNumber
    var insuranceCompany by CarInsuranceInfosTable.insuranceCompany
    var insuranceNumber by CarInsuranceInfosTable.insuranceNumber
    var insuranceGreenNumber by CarInsuranceInfosTable.insuranceGreenNumber
    var insuranceValidUntil by CarInsuranceInfosTable.insuranceValidUntil
    var comprehensiveInsurance by CarInsuranceInfosTable.comprehensiveInsurance
}

object CarInsuranceInfosTable : CommonTable("car_insurance_infos") {
    val policyholderName = varchar("policyholder_name", 16)
    val policyholderAddress = varchar("policyholder_address", 16)
    val policyholderNumber = varchar("policyholder_number", 16)
    val insuranceCompany = varchar("insurance_company", 16)
    val insuranceNumber = varchar("insurance_number", 16)
    val insuranceGreenNumber = varchar("insurance_green_number", 16)
    val insuranceValidUntil = date("insurance_valid_until")
    val comprehensiveInsurance = bool("comprehensive_insurance").nullable()
}
