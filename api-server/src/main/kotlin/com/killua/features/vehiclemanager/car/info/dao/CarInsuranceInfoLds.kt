package com.killua.features.vehiclemanager.car.info.dao

import com.killua.features.vehiclemanager.car.info.dao.data.CarInsuranceEntity
import com.killua.features.vehiclemanager.car.info.domain.model.CarInsuranceDto
import java.util.*

interface CarInsuranceInfoLds {
    fun getInsurancesInCompany(companyID: UUID)
    fun addCarInsurance(carInsurance: CarInsuranceDto): CarInsuranceEntity?
    fun updateCatInsurance(carInsurance: CarInsuranceDto): CarInsuranceEntity?
    fun removeCarInsurance(carInsuranceId: UUID)
    fun cleanCarInsuranceTable()
}