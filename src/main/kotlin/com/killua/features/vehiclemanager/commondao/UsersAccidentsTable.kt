package com.killua.features.vehiclemanager.commondao

import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import org.jetbrains.exposed.sql.Table

object UsersAccidentsTable : Table("users_accidents") {
    val user = reference("user", UserTable)
    val accident = reference("accident", AccidentTable)
    override val primaryKey = PrimaryKey(user, accident)
}