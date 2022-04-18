package com.killua.features.user.data.dao

import com.killua.config.Names.ADDITIONAL_INFO_COLUMN
import com.killua.config.Names.AREA_CODE_COLUMN
import com.killua.config.Names.CITY_COLUMN
import com.killua.config.Names.FIRSTNAME_COLUMN
import com.killua.config.Names.LASTNAME_COLUMN
import com.killua.config.Names.NATIONALITY_COLUMN
import com.killua.config.Names.PHONE_NUMBER_COLUMN
import com.killua.config.Names.SEX_COLUMN
import com.killua.config.Names.STREET_COLUMN
import com.killua.config.Names.STREET_NUMBER_COLUMN
import com.killua.config.Names.USER_INFO_TABLE
import com.killua.features.commondao.CommonEntity
import com.killua.features.commondao.CommonTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserInfoEntity(id: EntityID<UUID>) : CommonEntity(id, UserInfosTable) {
    companion object : UUIDEntityClass<UserInfoEntity>(UserInfosTable)
    var firstname by UserInfosTable.firstname
    var lastname by UserInfosTable.lastname
    var phoneNumber by UserInfosTable.phoneNumber
    var sex by UserInfosTable.sex
    var street by UserInfosTable.street
    var streetNr by UserInfosTable.streetNr
    var areaCode by UserInfosTable.areaCode
    var city by UserInfosTable.city
    var nationality by UserInfosTable.nationality
    var additionalInfo by UserInfosTable.additionalInfo

}

object UserInfosTable : CommonTable(USER_INFO_TABLE) {
    val firstname = varchar(FIRSTNAME_COLUMN, 32).nullable()
    val lastname = varchar(LASTNAME_COLUMN, 32).nullable()
    val phoneNumber = varchar(PHONE_NUMBER_COLUMN, 32).nullable()
    val nationality = varchar(NATIONALITY_COLUMN, 32).nullable()
    val sex = varchar(SEX_COLUMN, 6).nullable()
    val street = varchar(STREET_COLUMN, 32).nullable()
    val streetNr = varchar(STREET_NUMBER_COLUMN, 32).nullable()
    val city = varchar(CITY_COLUMN, 32).nullable()
    val areaCode = varchar(AREA_CODE_COLUMN, 9).nullable()
    val additionalInfo = text(ADDITIONAL_INFO_COLUMN).nullable()
}