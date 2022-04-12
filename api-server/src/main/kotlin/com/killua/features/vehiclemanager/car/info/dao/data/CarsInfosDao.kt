package com.killua.features.vehiclemanager.car.info.dao.data

import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.datetime
import java.util.*

class CarInsuranceEntity(id: EntityID<UUID>) : CommonEntity(id, CarInsuranceTable) {
    companion object : UUIDEntityClass<CarInsuranceEntity>(CarInsuranceTable)

    val cars by CarEntity referrersOn CarTable.carInsuranceInfo
    val company by CompanyEntity referencedOn CarInsuranceTable.company
    var policyholderName by CarInsuranceTable.policyholderName
    var policyholderAddress by CarInsuranceTable.policyholderAddress
    var policyholderNumber by CarInsuranceTable.policyholderNumber
    var insuranceCompany by CarInsuranceTable.insuranceCompany
    var insuranceNumber by CarInsuranceTable.insuranceNumber
    var insuranceGreenNumber by CarInsuranceTable.insuranceGreenNumber
    var insuranceValidUntil by CarInsuranceTable.insuranceValidUntil
    var comprehensiveInsurance by CarInsuranceTable.comprehensiveInsurance
}

object CarInsuranceTable : CommonTable("car_insurance_infos") {
    val company = reference("company", CompanyTable)
    val policyholderName = varchar("policyholder_name", 16)
    val policyholderAddress = varchar("policyholder_address", 16)
    val policyholderNumber = varchar("policyholder_number", 16)
    val insuranceCompany = varchar("insurance_company", 16)
    val insuranceNumber = varchar("insurance_number", 16)
    val insuranceGreenNumber = varchar("insurance_green_number", 16)
    val insuranceValidUntil = datetime("insurance_valid_until")
    val comprehensiveInsurance = bool("comprehensive_insurance").nullable()
}

