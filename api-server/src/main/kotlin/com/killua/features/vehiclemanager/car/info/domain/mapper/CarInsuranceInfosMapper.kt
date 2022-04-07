package com.killua.features.vehiclemanager.car.info.domain.mapper

import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.vehiclemanager.car.domain.mapper.toCarDto
import com.killua.features.vehiclemanager.car.info.dao.CarInsuranceEntity
import com.killua.features.vehiclemanager.car.info.domain.model.CarInsuranceDto


fun CarInsuranceEntity.toCarInfoDto(): CarInsuranceDto {
    return CarInsuranceDto(
        cars = cars.map { it.toCarDto() },
        company = company.toCompanyMentionDto(),
        policyholderName = policyholderName,
        policyholderAddress = policyholderAddress,
        policyholderNumber = policyholderNumber,
        insuranceCompany = insuranceCompany,
        insuranceNumber = insuranceNumber,
        insuranceGreenNumber = insuranceGreenNumber,
        insuranceValidUntil = insuranceValidUntil.millis,
        comprehensiveInsurance = comprehensiveInsurance ?: false
    )
}