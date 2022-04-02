package com.killua.features.vehiclemanager.commondao

import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.car.info.dao.CarInsuranceInfosTable
import org.jetbrains.exposed.sql.Table

object CarsInfosTable : Table("cars_infos") {
    val car = reference("car", CarTable).nullable()
    val information = reference("infos", CarInsuranceInfosTable).nullable()
    override val primaryKey = PrimaryKey(car, information)
}