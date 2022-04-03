package com.killua.features.vehiclemanager.commondao

import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import org.jetbrains.exposed.sql.Table

object UserCarsTable : Table("users_cars") {
    val car = reference("car", CarTable)
    val user = reference("user", UserTable)
    override val primaryKey = PrimaryKey(car, user)

}