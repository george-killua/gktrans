package com.killua.features.vehiclemanager.car.info.domain.model

import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.vehiclemanager.car.domain.model.CarDto

data class CarInsuranceDto(
    val cars: List<CarDto>? = null,
    val company:CompanyMentionDto,
    var policyholderName: String? = null,
    var policyholderAddress: String? = null,
    var policyholderNumber: String? = null,
    var insuranceCompany: String? = null,
    var insuranceNumber: String? = null,
    var insuranceGreenNumber: String? = null,
    var insuranceValidUntil: Long? = null,
    var comprehensiveInsurance: Boolean = false,
)

