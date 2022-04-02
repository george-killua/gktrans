package com.killua.features.user.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*
class UserInfoEntity(id: EntityID<UUID>) : CommonEntity(id,UserInfosTable) {
    companion object : UUIDEntityClass<UserInfoEntity>(UserInfosTable)

    var firstname by UserInfosTable.firstname
    var lastname by UserInfosTable.lastname
    var phoneNr by UserInfosTable.phoneNumber
    var sex by UserInfosTable.sex
    var street by UserInfosTable.street
    var streetNr by UserInfosTable.streetNr
    var areaCode by UserInfosTable.areaCode
    var city by UserInfosTable.city
    var nationality by UserInfosTable.nationality
    var additionalInfo by UserInfosTable.additionalInfo

}

object UserInfosTable : CommonTable("user_infos") {
    val firstname = varchar("firstname", 32).nullable()
    val lastname = varchar("lastname", 32).nullable()
    val phoneNumber = varchar("phone_number", 32).nullable()
    val nationality = varchar("nationality", 32).nullable()
    val sex = varchar("sex", 6).nullable()
    val street = varchar("street", 32).nullable()
    val streetNr = varchar("streetNr", 32).nullable()
    val city = varchar("city", 32).nullable()
    val areaCode = varchar("areaCode", 9).nullable()
    val additionalInfo = text("additional_info").nullable()
}