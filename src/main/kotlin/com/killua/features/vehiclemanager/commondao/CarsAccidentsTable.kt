package com.killua.features.vehiclemanager.commondao

import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import org.jetbrains.exposed.sql.Table


object CarsAccidentsTable : Table("cars_accidents") {
    val car = reference("car", CarTable)
    val accident = reference("accident", AccidentTable)
    override val primaryKey = PrimaryKey(car, accident)
}

