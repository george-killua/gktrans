package com.killua.features.user.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.domain.model.UserType
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.commondao.UserCarsTable
import com.killua.features.vehiclemanager.commondao.UsersAccidentsTable
import io.ktor.auth.*
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*
class UserEntity(id: EntityID<UUID>) : CommonEntity(id, UserTable),Principal {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var pic by ImageEntity optionalReferencedOn  ImagesTable.user
    var userType by UserTable.userType
    var email by UserTable.email
    var password by UserTable.password
    val carThatDriven by CarEntity backReferencedOn ( CarTable.currentDriver)
    val carsThatOwned by CarEntity referrersOn UserCarsTable.car
    val accidents by AccidentEntity referrersOn UsersAccidentsTable.accident
    val userInfo by UserInfoEntity optionalReferencedOn UserTable.userInfo
    var company by CompanyEntity optionalReferencedOn UserTable.company
}


object UserTable : CommonTable("users") {
    val userInfo = reference("user_info", UserInfosTable).nullable()
    val userType = enumerationByName("user_type", 8, UserType::class)
    val password = text("password").default("")
    val email = text("email").default("")
    val company = reference("company", CompanyTable).nullable()
}
