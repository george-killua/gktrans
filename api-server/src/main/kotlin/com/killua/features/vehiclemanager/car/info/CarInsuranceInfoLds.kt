package com.killua.features.vehiclemanager.car.info

import com.killua.features.vehiclemanager.car.info.dao.CarInsuranceEntity
import com.killua.features.vehiclemanager.car.info.domain.model.CarInsuranceDto
import java.util.UUID

interface CarInsuranceInfoLds {
    fun getInsurancesInCompany(companyID:UUID)
    fun addCarInsurance(carInsurance: CarInsuranceDto): CarInsuranceEntity?
    fun updateCatInsurance(carInsurance: CarInsuranceDto): CarInsuranceEntity?
    fun removeCarInsurance(carInsuranceId:UUID )
    fun cleanCarInsuranceTable()
}